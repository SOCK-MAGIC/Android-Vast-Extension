package cn.govast.vastutils.network.service

import cn.govast.vasttools.network.apicall.ApiCall
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/24
// Description: 
// Documentation:

interface UserService {

    @POST("/login/qr/key")
    suspend fun generateQRCode(@Query("timestamp") timestamp:String): QRCodeKey

    @GET("/search")
    fun searchSong(@Query("keywords") keywords:String,@Query("timestamp") timestamp:String): ApiCall<SongResult>

}