package com.nitsoft.ecommerce.api.review;

import com.nitsoft.ecommerce.api.APIName;
import com.nitsoft.ecommerce.api.APIUtil;
import com.nitsoft.ecommerce.api.response.APIStatus;
import com.nitsoft.ecommerce.api.response.StatusResponse;
import com.nitsoft.ecommerce.database.model.Review;
import com.nitsoft.ecommerce.service.ReviewService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIName.REVIEWS)
public class ReviewAPI extends APIUtil {

    @Autowired
    private ReviewService reviewService;

    @RequestMapping(value = APIName.REVIEWS_BY_PRODUCT_ID, method = RequestMethod.GET, produces = APIName.CHARSET)
    public String getReviewByProductId(@PathVariable(value = "id") int productId) {

        Review reviews = reviewService.findByProductId(productId);

        return writeObjectToJson(new StatusResponse(200, reviews));
    }

    @RequestMapping(value = APIName.REVIEWS_ADD, method = RequestMethod.POST, produces = APIName.CHARSET)
    private String createReview(@RequestParam(value = "comment") String comMent,
         @RequestParam(value = "company_id") int company_Id,@RequestParam(value = "product_id") int product_Id,
         @RequestParam(value = "rank") int Rank,@RequestParam(value = "user_id") String user_Id) throws IOException {
        Review reviews= new Review();
        reviews.setComment(comMent);
        reviews.setCompanyId(company_Id);
        reviews.setProductId(product_Id);
        reviews.setRank(Rank);
        reviews.setReviewId(Rank);
        reviews.setUserId(user_Id);
        
        reviewService.save(reviews);
        statusResponse = new StatusResponse(APIStatus.OK.getCode(), "Create Review successfully");
        return writeObjectToJson(statusResponse);
        
    }
}
