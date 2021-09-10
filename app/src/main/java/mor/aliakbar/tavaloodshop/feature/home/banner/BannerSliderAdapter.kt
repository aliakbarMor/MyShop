package mor.aliakbar.tavaloodshop.feature.home.banner

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import mor.aliakbar.tavaloodshop.model.dataclass.Banner
import mor.aliakbar.tavaloodshop.utils.Variable

class BannerSliderAdapter(fragment: Fragment, private val banners: List<Banner>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return banners.size
    }

    override fun createFragment(position: Int): Fragment {
        return BannerFragment.newInstance(Variable.EXTRA_KEY_DATA, banners[position])
    }
}