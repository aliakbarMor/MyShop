package mor.aliakbar.tavaloodshop.services.loaddingImage

import mor.aliakbar.tavaloodshop.R
import mor.aliakbar.tavaloodshop.customview.imageview.AppImageView
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FrescoLoadingImage @Inject constructor() : LoadingImageServices {
    override fun loadImage(imageView: AppImageView, imageUrl: String?) {
        if (imageUrl != null && imageUrl != "") {
//            imageView.setImageURI(Variable.BASE_URL + imageUrl)
            imageView
                .setImageURI(imageUrl)
        } else
            imageView.setActualImageResource(R.drawable.product_placeholder)

    }
}