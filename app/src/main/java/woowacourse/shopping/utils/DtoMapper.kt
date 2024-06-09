package woowacourse.shopping.utils

import woowacourse.shopping.data.remote.dto.cart.CartItemDto
import woowacourse.shopping.data.remote.dto.cart.CartItemQuantityDto
import woowacourse.shopping.data.remote.dto.cart.CartItemResponse
import woowacourse.shopping.data.remote.dto.order.CouponDto
import woowacourse.shopping.data.remote.dto.product.ProductDto
import woowacourse.shopping.data.remote.dto.product.ProductResponse
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.CartItemCounter
import woowacourse.shopping.domain.model.Coupon
import woowacourse.shopping.domain.model.ItemSelector
import woowacourse.shopping.domain.model.Product
import java.time.LocalDate
import java.time.LocalTime

object DtoMapper {
    fun CartItemResponse.toCartItemList(): List<CartItem> {
        return cartItemDto.map { it.toCartItem() }
    }

    fun CartItemDto.toCartItem(): CartItem {
        return CartItem(
            id = id,
            product = product.toProduct(quantity),
        )
    }

    fun CartItemQuantityDto.toQuantity(): Int {
        return quantity
    }

    fun ProductResponse.toProductList(): List<Product> {
        return productDto.map { it.toProduct() }
    }

    fun ProductDto.toProduct(quantity: Int = 0): Product {
        return Product(
            id = id.toLong(),
            name = name,
            imageUrl = imageUrl,
            price = price,
            category = category,
            cartItemCounter = CartItemCounter(quantity),
            itemSelector = ItemSelector(),
        )
    }

    fun CouponDto.toDomainModel(): Coupon {
        return when (this.code) {
            "FIXED5000" ->
                Coupon.FixedDiscountCoupon(
                    id = this.id,
                    code = this.code,
                    description = this.description,
                    expirationDate = this.expirationDate.toLocalDate(),
                    discountType = this.discountType,
                    discount = this.discount ?: 0,
                    minimumAmount = this.minimumAmount ?: 0,
                )
            "BOGO" ->
                Coupon.BogoCoupon(
                    id = this.id,
                    code = this.code,
                    description = this.description,
                    expirationDate = this.expirationDate.toLocalDate(),
                    discountType = this.discountType,
                    buyQuantity = this.buyQuantity ?: 0,
                    getQuantity = this.getQuantity ?: 0,
                )
            "FREESHIPPING" ->
                Coupon.FreeShippingCoupon(
                    id = this.id,
                    code = this.code,
                    description = this.description,
                    expirationDate = this.expirationDate.toLocalDate(),
                    discountType = this.discountType,
                    minimumAmount = this.minimumAmount ?: 0,
                )
            "MIRACLESALE" ->
                Coupon.TimeBasedDiscountCoupon(
                    id = this.id,
                    code = this.code,
                    description = this.description,
                    expirationDate = this.expirationDate.toLocalDate(),
                    discountType = this.discountType,
                    discount = this.discount ?: 0,
                    availableTimeStart = this.availableTime?.start?.toLocalTime() ?: LocalTime.MIN,
                    availableTimeEnd = this.availableTime?.end?.toLocalTime() ?: LocalTime.MAX,
                )
            else -> throw IllegalArgumentException("Unknown discount type: ${this.discountType}")
        }
    }

    private fun String.toLocalDate(): LocalDate {
        return LocalDate.parse(this)
    }

    private fun String.toLocalTime(): LocalTime {
        return LocalTime.parse(this)
    }
}
