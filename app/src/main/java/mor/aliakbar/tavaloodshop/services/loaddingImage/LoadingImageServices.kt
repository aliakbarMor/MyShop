package mor.aliakbar.tavaloodshop.services.loaddingImage

import mor.aliakbar.tavaloodshop.customview.imageview.AppImageView

interface LoadingImageServices {

    fun loadImage(imageView: AppImageView, imageUrl: String?)
}