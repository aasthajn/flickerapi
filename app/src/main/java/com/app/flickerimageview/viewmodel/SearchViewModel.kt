package com.app.flickerimageview.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.flickerimageview.model.ImageRepository
import com.app.flickerimageview.model.Photos
import com.app.flickerimageview.model.SearchItem
import com.app.flickerimageview.network.ErrorResponse
import com.app.flickerimageview.network.Output
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    val resultLiveData = MutableLiveData<Photos>()

    val errorLiveData = MutableLiveData<ErrorResponse>()
    private val imageRepository: ImageRepository = ImageRepository(application)
    var isLoadingPagination = false
    val isLoading = MutableLiveData<Boolean>()
    var totalPages=1
    var page =0

    fun getResults(searchText: String) {
        isLoading.postValue(true)
        isLoadingPagination = true

        GlobalScope.launch(Dispatchers.IO) {
            //get latest news from news repo
            isLoadingPagination = false
            val resultResponse = imageRepository.getResults(searchText)
            isLoading.postValue(false)
            if (resultResponse is Output.Success) {
                if (resultResponse.output is SearchItem) {
                    if (resultResponse.output.stat == "ok") {
                        totalPages = resultResponse.output.photos.pages
                        page = resultResponse.output.photos.page
                        resultLiveData.postValue(resultResponse.output.photos)
                    }
                }
            } else if (resultResponse is Output.Error) {
                errorLiveData.postValue(resultResponse.errorMessage as ErrorResponse)
            }
        }
    }

}