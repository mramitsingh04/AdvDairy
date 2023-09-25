package com.generic.khatabook.rating.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Argument fount.")
public class LimitBonusException extends RuntimeException {

    public LimitBonusException(final AppEntity appEntity, final String invalidMember) {
        super(appEntity.getName() + " don't have " + invalidMember + "member, invalid argument fount.!.");
    }


    public LimitBonusException(final AppEntity appEntity, Limit limit, final float rating) {
        super(appEntity.getName() +" " + limit.getLimit()+ " "+ rating + " bonus.");
    }

    public ProblemDetail get() {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, super.getMessage());
    }

    public enum Limit {
        MAX("Maximum"), MIN("Minimum");

        private final String myLimit;

        Limit(final String limit) {
            myLimit = limit;
        }

        public String getLimit() {
            return myLimit;
        }
    }


}
