package com.example.demo.api

import com.example.demo.Table.*
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

//Services
@CrossOrigin()
@RestController
@RequestMapping
class APIRouter(val jdbcTemplate: JdbcTemplate) {

    // Customer API
    @PostMapping("/GetCustomerDetails")
    fun getCustomerDetails(
            @RequestParam("custID", required = true) custID: String,
            @RequestParam("custPassword", required = true) custPassword: String,
            @RequestParam("custMobile", required = false, defaultValue = "") custMobile: String
    ) = CustomerDetailsWS().getCustomer(custID, custPassword, custMobile)

    @PostMapping("/PostCustomerDetails")
    fun PostCustomerDetails(
            @RequestParam("name", required = true) name: String,
            @RequestParam("email", required = true) email: String,
            @RequestParam("mobile", required = true) mobile: String,
            @RequestParam("housing", required = true) housing: String,
            @RequestParam("password", required = true) password: String,
            @RequestParam("imageFile", required = false) imageFile: MultipartFile?
    ) = CustomerDetailsWS().custRegistration(name, email, mobile, housing, password, imageFile)

    @PostMapping("/ForgetPassword")
    fun forgetPassword(
            @RequestParam("mobile", required = true) mobile: String,
            @RequestParam("password", required = true) password: String
    ) = CustomerDetailsWS().forgetPasword(mobile, password)


    //ADMIN LOGIN(WORK IS PENDING)
    @PostMapping("/AdminLogin")
    fun adminLogin(
            @RequestParam("email", required = true) email: String,
            @RequestParam("password") password: String
    ) =AdminPanelWS().adminLogin(email, password)


    // AD_SLIDER API
    @PostMapping("/GetADD")
    fun adSlider()=AdSliderWS().getAllAdSlider()

    @PostMapping("/PostADD")
    fun createAdSlider(
            @RequestParam("title",required = true)title:String,
            @RequestParam("description",required = false)description:String,
            @RequestParam("image",required = true)image:MultipartFile
    )=AdSliderWS().createAdSlider(title,description,image)

    @PostMapping("/editADD")
    fun editAdSlider(
            @RequestParam("id",required=true)id:Int,
            @RequestParam("title",required = true)title:String,
            @RequestParam("description",required = false)description:String,
            @RequestParam("image",required = true)image:MultipartFile
    )=AdSliderWS().editAdSlider(id,title,description,image)

    @PostMapping("/deleteADD")
    fun deleteAdSlider(
            @RequestParam("id",required=true)id:Int
    )=AdSliderWS().deleteAdSlider(id)


    //Service Category API

    @PostMapping("/getServiceCatWithBannerImage")
    fun getServiceCatBannerImage(
            @RequestParam("id",required=true)id:Int
    )=ServiceCategaryWS().getServiceCatBannerImage(id)


    @PostMapping("/PostServiceCat")
    fun postServiceCat(
            @RequestParam("name",required = true)name:String,
            @RequestParam("description",required = true)description:String,
            @RequestParam("image",required = false)image:MultipartFile
    )=ServiceCategaryWS().postServiceCat(name,description,image)

    @PostMapping("/UpdateServiceCat")
    fun editServiceCat(
            @RequestParam("id",required = true)id:Int,
            @RequestParam("name",required = true)name:String,
            @RequestParam("description",required = true)description:String,
            @RequestParam("image",required = false)image:MultipartFile
    )=ServiceCategaryWS().editServiceCat(id,name,description,image)


    @PostMapping("/deleteServiceCat")
    fun deleteServiceCat(
            @RequestParam("id",required=true)id:Int
    )=ServiceCategaryWS().deleteServiceCat(id)


    @PostMapping("/getServiceCatWithService")
    fun getServiceCat(
            @RequestParam("id",required=true)id:Int
    )=ServiceCategaryWS().getServiceCat(id)

//    @PostMapping("/GetTechnicianDetails")
//    fun GetTechDetails(@RequestParam("techID", required = true) techID: String) = TechnicianWS().GetTechnicianDetails(techID)
}