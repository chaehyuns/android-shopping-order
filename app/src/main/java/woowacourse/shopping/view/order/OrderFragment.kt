package woowacourse.shopping.view.order

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.shopping.data.repository.remote.RemoteOrderRepositoryImpl
import woowacourse.shopping.databinding.FragmentOrderBinding
import woowacourse.shopping.view.MainActivityListener
import woowacourse.shopping.view.ViewModelFactory

class OrderFragment : Fragment(), OnClickOrder {
    private var mainActivityListener: MainActivityListener? = null
    private var _binding: FragmentOrderBinding? = null
    val binding: FragmentOrderBinding get() = _binding!!
    private val orderViewModel: OrderViewModel by lazy {
        val viewModelFactory =
            ViewModelFactory {
                OrderViewModel(
                    orderRepository = RemoteOrderRepositoryImpl(),
                )
            }
        viewModelFactory.create(OrderViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivityListener) {
            mainActivityListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = orderViewModel
    }

    override fun clickOrder() {
        TODO("Not yet implemented")
    }

    override fun clickBack() {
        mainActivityListener?.popFragment()
    }
}