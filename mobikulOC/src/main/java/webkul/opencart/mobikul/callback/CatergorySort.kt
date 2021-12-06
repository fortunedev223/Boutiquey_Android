package webkul.opencart.mobikul.callback

import webkul.opencart.mobikul.model.ProductCategory.ProductCategory
import webkul.opencart.mobikul.model.ProductSearch.ProductSearch

/**
 * Created by manish.choudhary on 8/8/17.
 */

interface CatergorySort {
    fun getproductCategorySortBy(productCategory: ProductCategory)
    fun getproductSearchSortBy(productSearch: ProductSearch)
}
