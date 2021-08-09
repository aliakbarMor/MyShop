package mor.aliakbar.tavaloodshop.view.home.banner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import mor.aliakbar.tavaloodshop.databinding.FragmentBannerBinding
import mor.aliakbar.tavaloodshop.model.dataclass.Banner
import mor.aliakbar.tavaloodshop.services.LoadingImageServices
import mor.aliakbar.tavaloodshop.utils.Variable.EXTRA_KEY_DATA
import javax.inject.Inject

@AndroidEntryPoint
class BannerFragment : Fragment() {

    private lateinit var binding: FragmentBannerBinding

    @Inject
    lateinit var imageLoadingService: LoadingImageServices

    companion object {
        fun newInstance(key: String, banner: Banner): BannerFragment {
            return BannerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(key, banner)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBannerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val banner = requireArguments().getParcelable<Banner>(EXTRA_KEY_DATA)!!
        imageLoadingService.loadImage(binding.sliderImage, banner.url)

    }


}