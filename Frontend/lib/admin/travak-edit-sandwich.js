import DenTravakAbstractElement from '../travak-abstract-element.js';
import '../common/travak-input-text.js';

class DenTravakEditSandwich extends DenTravakAbstractElement {

    constructor() {
        super('travak-admin-app')
    }

    connectedCallback() {
        super.connectedCallback();
        this.initEventListeners();
    }

    initEventListeners() {
        this.byId('save-btn').addEventListener('click', e => this.saveSandwich());
        this.byId('cancel-btn').addEventListener('click', e => this.app().showSandwichList());
        this.byId('delete-btn').addEventListener('click', e => this.deleteSandwich());
    }

    saveSandwich() {
        //todo: call backend via fetch api and save sandwich
        this.sandwich.name = this.byId('name').value;
        this.sandwich.ingredients = this.byId('ingredients').value;
        this.sandwich.price = this.byId('price').value;
        let m = 'POST';
        let link = 'http://localhost:8080/sandwiches';
        if (this.sandwich.id) {
            m = 'PUT';
            link += ('/' + this.sandwich.id);
        }
        console.log(link);
        (async () => {
            const rawResponse = await fetch(link, {
                method: m,
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(this.sandwich)
            });
            const content = await rawResponse.json();
            if (await rawResponse.ok()) {this.app().dispatchEvent(new CustomEvent('save-succeeded', { detail: this.sandwich }));}
            else; //error
            
        })();
    }

    deleteSandwich() {
        (async () => {
            const rawResponse = await fetch('http://localhost:8080/sandwiches/' + this.sandwich.id, {
                method: 'DELETE',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            });
            //const content = await rawResponse.json();

            this.app().dispatchEvent(new CustomEvent('delete-succeeded', { detail: this.sandwich }));
        })();
    }

    init(sandwich) {
        if (sandwich) {
            this.sandwich = sandwich;
            this.byId('title').innerHTML = `Bewerk broodje ${sandwich.name}`;
            this.byId('name').value = sandwich.name;
            this.byId('ingredients').value = sandwich.ingredients;
            this.byId('price').value = sandwich.price;
            this.byId('delete-btn').style.visibility = "visible";
        } else {
            this.sandwich = {};
            this.byId('title').innerHTML = `Maak een nieuw broodje`;
            this.byId('name').value = "";
            this.byId('ingredients').value = "";
            this.byId('price').value = "";            
            this.byId('delete-btn').style.visibility = "hidden";
        }
    }

    get template() {
        return `
            <h4 id="title">Bewerk een broodje</h4>
            <travak-input-text id="name" label="Sandwich naam" placeholder="De naam van het broodje"></travak-input-text>
            <travak-input-text id="ingredients" label="Ingredienten" placeholder="Welke ingredienten heeft het broodje"></travak-input-text>
            <travak-input-text id="price" label="Prijs" placeholder="De prijs van het broodje"></travak-input-text>
            <button id="cancel-btn" type="button" class="btn">Cancel</button>
            <button id="save-btn" type="button" class="btn btn-primary">Save</button>
            <button id="delete-btn" type="button" class="btn btn-danger">Delete</button>
        `;
    }

}

customElements.define('travak-edit-sandwich', DenTravakEditSandwich);