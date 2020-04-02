package com.app.flickerimageview.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.flickerimageview.model.*
import com.app.flickerimageview.network.ErrorResponse
import com.app.flickerimageview.network.Output
import com.app.flickerimageview.utils.ConnectionLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    val resultLiveData = MutableLiveData<ArrayList<ListItem>>()
    private val imageRepository: ImageRepository = ImageRepository(application)
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    private lateinit var loaderText: String
    private var currentPage = 0
    private var totalPages = 0
    private var photoList = arrayListOf<Photo>()
    private var loadingInProgress = false

    init {
        reset()
    }

    fun getResults(searchText: String) {
        if (!loadingInProgress) {
            if (searchText.isEmpty()) {
                errorMessage.postValue("Enter at least 1 character")
                return
            }
            if (photoList.isNotEmpty()) {
                showLoader()
            }
            isLoading.postValue(true)
            loadingInProgress = true
            GlobalScope.launch(Dispatchers.IO) {
                val resultResponse = imageRepository.getResults(searchText, currentPage + 1)
                isLoading.postValue(false)
                loadingInProgress = false
                if (resultResponse is Output.Success) {
                    if (resultResponse.output is SearchItem) {
                        if (resultResponse.output.stat == "ok") {
                            resultResponse.output.photos?.photo?.let {
                                photoList.addAll(it)
                                val list = arrayListOf<ListItem>()
                                list.addAll(photoList)
                                resultLiveData.postValue(list)
                                currentPage = resultResponse.output.photos.page
                                totalPages = resultResponse.output.photos.pages
                            }
                        } else {
                            errorMessage.postValue(resultResponse.output.message)
                        }
                    }
                } else if (resultResponse is Output.Error) {
                    errorMessage.postValue((resultResponse.errorMessage as ErrorResponse).message)
                }
            }
        }
    }

    fun checkPhotoListEmpty(): Boolean {
        return photoList.isEmpty()
    }

    private fun showLoader() {
        val list = resultLiveData.value
        list?.add(PageLoader(loaderText))
        resultLiveData.postValue(list)
    }

    fun isValidPageRequest(): Boolean {
        return currentPage < totalPages && totalPages>0
    }

    fun reset() {
        photoList.clear()
        resultLiveData.postValue(photoList as ArrayList<ListItem>)
        currentPage = 0
        totalPages = Int.MAX_VALUE
        loaderText = "Loading"
        loadingInProgress = false
    }

}