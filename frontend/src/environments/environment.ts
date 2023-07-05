const baseUrl = "http://localhost:9192/api"
export const environment = {
    statsUrl : baseUrl + "/stats",
    userLoginUrl : baseUrl + "/user/login",
    userGetReviewsUrl : baseUrl + "/user/get/reviews/",
    userRegisterUrl : baseUrl + "/user/register",
    userPostReviewUrl: baseUrl + "/user/post/review/",
    productSearchUrl : baseUrl + "/user/product/search/",
    adminLoginUrl: baseUrl + "/admin/login",
    adminGetReviewsUrl: baseUrl + "/admin/get/reviews",
    adminApproveReviewUrl: baseUrl + "/admin/approve/review/",
    adminRejectReviewUrl: baseUrl + "/admin/reject/review/",
    userRequestReviewUrl: baseUrl + "/user/request/review",
    userGetProductUrl: baseUrl + "/user/get/product/"
}