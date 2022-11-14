package com.raassh.gemastik15.view.fragments.read

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.raassh.gemastik15.adapter.ArticleAdapter
import com.raassh.gemastik15.databinding.FragmentArticlesBinding
import com.raassh.gemastik15.utils.Resource
import com.raassh.gemastik15.utils.showSnackbar
import com.raassh.gemastik15.utils.translateErrorMessage
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticlesFragment : Fragment() {
    private val viewModel by viewModel<ReadViewModel>()
    private var binding: FragmentArticlesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticlesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val articlesAdapter = ArticleAdapter().apply {
            onItemClickListener = { article ->
                val action =
                    ReadFragmentDirections.actionNavigationReadToWebViewerFragment(
                        article.url
                    )
                findNavController().navigate(action)
            }
        }

        binding?.apply {
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL).let {
                rvArticles.addItemDecoration(it)
            }

            rvArticles.adapter = articlesAdapter
        }

        viewModel.articles.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }
                    is Resource.Success -> {
                        showLoading(false)

                        articlesAdapter.submitList(it.data)
                    }
                    is Resource.Error -> {
                        showLoading(false, error = true)

                        binding?.root?.showSnackbar(
                            requireContext().translateErrorMessage(it.message)
                        )
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean, error: Boolean = false) {
        binding?.apply {
            if (isLoading) {
                pbLoading.visibility = View.VISIBLE
                rvArticles.visibility = View.GONE
            } else {
                pbLoading.visibility = View.GONE
                rvArticles.visibility = View.VISIBLE
            }
        }
    }
}