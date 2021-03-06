import DenTravakAbstractElement from '../travak-abstract-element.js';

class DenTravakSandwichesCheckout extends DenTravakAbstractElement {

    connectedCallback() {
        super.connectedCallback();
        this.initEventListeners();
    }

    init(sandwich) {
        this.sandwich = sandwich;
        this.byId('sandwiches').innerHTML = ``;
        this.byId('sandwiches').appendChild(htmlToElement(this.getSandwichTemplate(this.sandwich)));
    }

    initEventListeners() {
        this.byId('order-button').addEventListener('click', e => this.orderSandwich());
        this.byId('back-button').addEventListener('click', e => this.app().showSandwichList());
    }

    orderSandwich() {
        (async () => {
            const rawResponse = await fetch('http://localhost:8080/orders', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    sandwichId: this.sandwich.id,
                    name: this.sandwich.name,
                    breadType: this.byCss('input[name="typeBrood"]:checked').value.toUpperCase(),
                    price: this.sandwich.price,
                    mobilePhoneNumber: this.byId('mobile-phone-number').value
                })
            });
            const content = await rawResponse.json();

            this.app().dispatchEvent(new CustomEvent('order-succeeded', {detail: content}));
        })();
    }

    async injectBreadTypes() {
        const resp = await fetch('http://localhost:8080/breadtypes');
        const json = await resp.json();

        const result = json.map(bt => `
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="typeBrood" id="radio${bt}" value="${bt}">
                    <label class="form-check-label" for="radio${bt}">
                        ${bt.charAt(0) + bt.slice(1).toLowerCase().replace(/_/g, " ")}
                    </label>
                </div>`).join('');
        this.byId("breadtypes").innerHTML += result;
    }


    get template() {
        this.injectBreadTypes();
        return `
                <style>
                    .form-group {
                        margin-bottom: 2rem;
                    }
                    .dt-header {
                        display: flex;
                    }
                    .dt-header button {
                        margin-left: auto;
                    }
                    div.dt-sandwich-info {
                        margin-left: auto;
                    }
                </style>
                <div class="animate">
                    <div class="dt-header">
                        <h3>Welkom bij den Travak</h3>
                        <button id="back-button" type="button" class="btn btn-primary">Terug</button>
                    </div>
                    <h4>Je geselecteerde broodje</h4>
                    <div>
                    <ul id="sandwiches" class="list-group"></ul>
                    </div>
                    <div class="form-group" id="breadtypes">
                        <label for="typeBrood"><h4>Kies het type brood</h4></label>
                        <!-- here come the breadtypes -->
                    </div>
                    <div class="form-group">
                        <label for="mobile-phone-number"><h4>Je GSM Nummer</h4></label>
                        <input type="text" class="form-control" id="mobile-phone-number" placeholder="0487/12 34 56">
                    </div>

                    <button id="order-button" class="btn btn-primary active">Bestellen</button>
                </div>
            `;

    }

    // <div class="form-check">
    //     <input class="form-check-input" type="radio" name="typeBrood" id="radioBoterhammekes" value="Boterhammekes">
    //     <label class="form-check-label" for="radioBoterhammekes">
    //         Boterhammekes
    //     </label>
    // </div>
    // <div class="form-check">
    //     <input class="form-check-input" type="radio" name="typeBrood" id="radioWrap" value="Wrap">
    //     <label class="form-check-label" for="radioWrap">
    //         Wrap
    //     </label>
    // </div>
    // <div class="form-check">
    //     <input class="form-check-input" type="radio" name="typeBrood" id="radioTurksBrood" value="TurksBrood">
    //     <label class="form-check-label" for="radioTurksBrood">
    //         Turks brood
    //     </label>
    // </div>


    getSandwichTemplate(sandwich) {
        return `
            <a class="list-group-item">
                <button type="button" class="btn btn-primary bmd-btn-fab">
                    ${sandwich.name.charAt(0)}
                </button>
                <div class="bmd-list-group-col">
                    <p class="list-group-item-heading">${sandwich.name}</p>
                    <p class="list-group-item-text">${sandwich.ingredients}</p>
                </div>
                <div class="dt-sandwich-info">
                    <p class="list-group-item-text">${sandwich.price}</p>
                </div>
            </a>
        `;
    }
}

customElements.define('travak-sandwiches-checkout', DenTravakSandwichesCheckout);