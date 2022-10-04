class App {

    static SweetAlert = class {
        static showSuccessAlert(t) {
            Swal.fire({
                icon: 'success',
                title: t,
                position: 'top-end',
                showConfirmButton: false,
                timer: 1500
            })
        }

        static showErrorAlert(t) {
            Swal.fire({
                icon: 'error',
                title: 'Warning',
                text: t,
            })
        }
        static showConfirmDeleteUser() {
            return Swal.fire({
                title: 'Do you want to remove this user?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, please!',
                cancelButtonText: 'Cancel'
            });
        }
        static showConfirmDeleteProduct() {
            return Swal.fire({
                title: 'Do you want to remove this product?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, please!',
                cancelButtonText: 'Cancel'
            });
        }
    }

    static IziToast = class  {
        static showSuccessAlert(m) {
            iziToast.success({
                title: 'Success',
                position: 'topRight',
                timeout: 2500,
                message: m,
            });
        }

        static showErrorAlert(m) {
            iziToast.error({
                title: 'Error',
                position: 'topRight',
                timeout: 2500,
                message: m,
            });
        }
    }

    static formatNumber() {
        $(".num-space").number(true, 0, ',', ' ');
        $(".num-point").number(true, 0, ',', '.');
        $(".num-comma").number(true, 0, ',', ',');
    }

    static formatNumberSpace(x) {
        if (x == null) {
            return x;
        }
        return x.toString().replace(/ /g, "").replace(/\B(?=(\d{3})+(?!\d))/g, " ");
    }
}

class LocationRegion {
    constructor(id, provinceId, provinceName, districtId, districtName, wardId, wardName, address) {
        this.id = id;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.wardId = wardId;
        this.wardName = wardName;
        this.address = address;
    }
}

class User {
    constructor(id, fullName, email, password, phone, locationRegion, role, urlImage) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.locationRegion = locationRegion
        this.role = role;
        this.urlImage = urlImage;
    }
}
class Role {
    constructor(id ,code, name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }
}
class Product{
    constructor(id,title,price,urlImage,category,description) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.urlImage = urlImage;
        this.category = category;
        this.description = description;
    }
}
class Category {
    constructor(id,title) {
        this.id = id;
        this.title = title;
    }

}
