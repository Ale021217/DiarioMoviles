package com.ale.a14_firebaseaccess.entites

class cls_Category {

    var CategoryID: Int = 0
    var CategoryName: String = ""
    var Description: String = ""
    var urlImage: String = ""

    constructor() {}

    constructor(CategoryID: Int, CategoryName: String, Description: String, urlImage: String) {
        this.CategoryID = CategoryID
        this.CategoryName = CategoryName
        this.Description = Description
        this.urlImage = urlImage
    }


}