package mor.aliakbar.tavaloodshop.model.source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mor.aliakbar.tavaloodshop.model.api.ApiService
import mor.aliakbar.tavaloodshop.model.dataclass.CategoryItem
import mor.aliakbar.tavaloodshop.model.dataclass.CategoryItemParent
import javax.inject.Inject

class CategoryRemoteSource @Inject constructor(private val apiService: ApiService) :
    CategoryDataSource {

    override fun getCategories(): Flow<List<CategoryItem>> {
        return flow {
            emit(
                ArrayList<CategoryItem>().apply {
                    add(CategoryItem(0, "شمع عدد", "", 0))
                    add(CategoryItem(1, "شمع حروف", "", 0))
                    add(CategoryItem(2, "شمع هپی و لاو", "", 0))
                    add(CategoryItem(3, "شمع شخصیت", "", 0))
                    add(CategoryItem(4, "شمع وارمر", "", 0))
                    add(CategoryItem(4, "بادکنک ساده", "", 1))
                    add(CategoryItem(4, "بادکنک خالدار", "", 1))
                    add(CategoryItem(4, "بادکنک کروم", "", 1))
                    add(CategoryItem(4, "بادکنک پاستیلی", "", 1))
                    add(CategoryItem(4, "بادکنک طرح دار", "", 1))
                    add(CategoryItem(4, "بادکنک 6 اینچ", "", 1))
                    add(CategoryItem(4, "لوازم جانبی بادکنک", "", 1))
                }
            )
        }
    }

    override fun getParentCategories(): Flow<List<CategoryItemParent>> {
        return flow {
            emit(ArrayList<CategoryItemParent>().apply {
                add(CategoryItemParent(0, "شمع", ""))
                add(CategoryItemParent(1, "بادکنک", ""))
                add(CategoryItemParent(2, "کلاه", ""))
                add(CategoryItemParent(3, "کادو", ""))
                add(CategoryItemParent(4, "تزیینات جشن", ""))
                add(CategoryItemParent(5, "آتش بازی", ""))
                add(CategoryItemParent(6, "تم", ""))
            })
        }
    }
}