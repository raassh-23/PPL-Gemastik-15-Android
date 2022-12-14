package com.raassh.gemastik15.view.fragments.searchuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.raassh.gemastik15.R
import com.raassh.gemastik15.adapter.SearchUserPagerAdapter
import com.raassh.gemastik15.databinding.FragmentSearchUserBinding
import com.raassh.gemastik15.utils.on
import com.raassh.gemastik15.utils.showSnackbar
import dev.chrisbanes.insetter.applyInsetter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchUserFragment : Fragment() {
    private val viewModel by sharedViewModel<SearchUserViewModel>()
    private var binding: FragmentSearchUserBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchUserBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val query = SearchUserFragmentArgs.fromBundle(requireArguments()).query

        binding?.apply {
            root.applyInsetter { type(statusBars = true, navigationBars = true) { padding() } }
            etSearch.setText(query)
            etSearch.on(EditorInfo.IME_ACTION_DONE) {
                val username = etSearch.text.toString()
                if (username.isEmpty()) {
                    root.showSnackbar(
                        message = getString(R.string.empty_query),
                    )
                    return@on
                }

                val action = SearchUserFragmentDirections.actionSearchUserFragmentSelf(username)
                findNavController().navigate(action)
            }

            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            viewPager.adapter = SearchUserPagerAdapter(this@SearchUserFragment)

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = getString(R.string.message)
                    }
                    1 -> {
                        tab.text = getString(R.string.user)
                    }
                }
            }.attach()
        }

        viewModel.setQuery(query)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}