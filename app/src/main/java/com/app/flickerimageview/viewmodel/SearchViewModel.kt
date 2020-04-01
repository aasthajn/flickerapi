package com.app.flickerimageview.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.flickerimageview.model.*
import com.app.flickerimageview.network.ErrorResponse
import com.app.flickerimageview.network.Output
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    val resultLiveData = MutableLiveData<ArrayList<ListItem>>()
    private val imageRepository: ImageRepository = ImageRepository(application)
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    private var currentPage = 0
    private var totalPages = 0
    private var photoList = arrayListOf<Photo>()

    init {
        reset()
    }

    fun getResults(searchText: String) {
        if (searchText.isEmpty()) {
            errorMessage.postValue("Enter at least 1 character")
            return
        }
        isLoading.postValue(true)
        if (photoList.isNotEmpty()) {
            showLoader()
        }

        GlobalScope.launch(Dispatchers.IO) {
            val resultResponse = imageRepository.getResults(searchText, currentPage + 1)
            isLoading.postValue(false)
            if (resultResponse is Output.Success) {
                if (resultResponse.output is SearchItem) {
                    if (resultResponse.output.stat == "ok") {
                        resultResponse.output.photos.photo?.let {
                            photoList.addAll(it)
                        }
                    }
                    val list = arrayListOf<ListItem>()
                    list.addAll(photoList)
                    resultLiveData.postValue(list)
                    currentPage = resultResponse.output.photos.page
                    totalPages = resultResponse.output.photos.pages
                }
            } else if (resultResponse is Output.Error) {
                errorMessage.postValue((resultResponse.errorMessage as ErrorResponse).message)
            }
        }
    }

    private fun showLoader() {
        val list = resultLiveData.value
        list?.add(PageLoader("Loading..."))
        resultLiveData.postValue(list)
    }

    fun isValidPageRequest(): Boolean {
        return currentPage < totalPages
    }

    fun reset() {
        photoList.clear()
        resultLiveData.postValue(photoList as ArrayList<ListItem>)
        currentPage = 0
        totalPages = Int.MAX_VALUE
    }

}