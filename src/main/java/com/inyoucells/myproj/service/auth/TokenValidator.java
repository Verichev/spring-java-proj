package com.inyoucells.myproj.service.auth;

import com.inyoucells.myproj.models.ApiError;
import com.inyoucells.myproj.models.TokenValidationResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class TokenValidator {

    public TokenValidationResult check(String token) {
        if (token.isEmpty()) {
            return new TokenValidationResult(null, new ApiError(HttpStatus.UNAUTHORIZED, "Unauthorised request"));
        }
        String[] sp = token.split("_");
        Calendar calendar = Calendar.getInstance();
        Long userId = null;
        Long expired = null;

        try {
            expired = Long.parseLong(sp[1]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            userId = Long.parseLong(sp[0]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (userId == null || expired != null && calendar.getTimeInMillis() > expired) {
            ApiError apiError =
                    new ApiError(HttpStatus.UNAUTHORIZED, "Authorization is outdated");
            return new TokenValidationResult(null, apiError);

        } else {
            return new TokenValidationResult(userId, null);
        }
    }
}
