class AppPage {
    static renderProductItem(obj) {
        return `
                <div class="col-xl-3 col-lg-3 col-md-6 col-sm-12 margintop">
                            <div class="brand-box">
                                <h5>Sale</h5>
                                <i><img src="/assets/icon/${obj.urlImage}"/>
                                </i>
                                <h3 class="card-title">${obj.title}</h3>
                                <h4>Price $<span class="nolmal">${obj.price}</span></h4>

                            </div>
                            <a class="buynow add-cart" id="addToCart" data-id="${obj.id}">Add to cart</a>
                </div>
        `;
    }
    static renderCartItem(obj) {
        return `
            <div id="ci_${obj.id}" class="card mb-3" style="max-width: 100%;">
                <div class="row g-0">
                    <div class="col-md-4">
                        <img src="/assets/icon/${obj.urlImage}" width="150px" class="img-fluid rounded-start" alt="...">
                    </div>
                    <div class="col-md-8">
                        <div class="card-body">
                            <h5 class="card-title">${obj.productName}</h5>
                            <p class="card-text">
                                <span class="price">${obj.productPrice}</span>
                                <span>
                                    <button class="btn btn-danger minus" data-id="${obj.id}">-</button>
                                </span>
                                <span>
                                    <input type="text" class="form-control quantity" data-id="${obj.id}" value="${obj.quantity}">
                                </span>
                                <span>
                                    <button class="btn btn-success add" data-id="${obj.id}">+</button>
                                </span>
                                <span class="amount">${obj.amount}</span>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        `;
    }
}
// <div class="content mr2 fl">
//     <div class="card" style="width: 18rem;">
//         <img src="/assets/img/${obj.image}" class="card-img-top" alt="...">
//         <div class="card-body">
//             <h5 class="card-title">${obj.name}</h5>
//             <p class="card-text">${obj.price} vnđ</p>
//             <a href="#" class="btn btn-primary add-cart" data-id="${obj.id}">Add to cart</a>
//         </div>
//     </div>
// </div>