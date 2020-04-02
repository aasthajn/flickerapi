package com.app.flickerimageview.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.flickerimageview.R
import com.app.flickerimageview.utils.ConnectionLiveData
import com.app.flickerimageview.utils.PaginationListener
import com.app.flickerimageview.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var displayListAdapter: DisplayListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var paginationListener: PaginationListener

    private val mainViewModel: SearchViewModel by lazy {
        ViewModelProviders.of(this).get(SearchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        displayListAdapter = DisplayListAdapter()
        linearLayoutManager = LinearLayoutManager(context)
        bindView()
        subscribe()
    }

    private fun subscribe() {
        mainViewModel.resultLiveData.observe(viewLifecycleOwner, Observer { result ->
            result?.run {
                displayListAdapter.list = this
                toggleTextAndProgress(false)
            }
        })

        mainViewModel.errorMessage.observe(viewLifecycleOwner, Observer { error ->
            setResultErrorText(error)
        })

        mainViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it)
                toggleTextAndProgress(it)
        })

        ConnectionLiveData(context!!).observe(viewLifecycleOwner, Observer { status ->
            if (!status) {
                bottom_view.visibility = View.VISIBLE
            } else {
                bottom_view.visibility = View.GONE
                getImages()
            }
        })
    }

    private fun toggleTextAndProgress(isLoading: Boolean) {
        if (isLoading) {
            if (mainViewModel.checkPhotoListEmpty())
                progress_circular.visibility = View.VISIBLE
            tv_no_results.visibility = View.GONE
        } else {
            progress_circular.visibility = View.GONE
            if (mainViewModel.checkPhotoListEmpty())
                tv_no_results.visibility = View.VISIBLE
        }

    }

    private fun bindView() {
        paginationListener = object : PaginationListener(linearLayoutManager) {
            override fun loadMoreItems() {
                getImages()
            }
        }

        rv_search_results.apply {
            layoutManager = linearLayoutManager
            adapter = displayListAdapter
            addOnScrollListener(paginationListener)
        }

        ed_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                resetParams()
                getImages()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
    }

    private fun getImages() {
        if (mainViewModel.isValidPageRequest()) {
            mainViewModel.getResults(ed_search.text.toString().trim())
        }
    }

    private fun setResultErrorText(error: String) {
        toggleTextAndProgress(false)
        if (mainViewModel.checkPhotoListEmpty()) {
            tv_no_results.text = error
        } else {
            showToast(error)
        }
    }

    private fun showToast(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    private fun resetParams() {
        mainViewModel.reset()
    }
}
