package com.inyoucells.myproj.service.auth;

import com.inyoucells.myproj.models.HttpError;
import com.inyoucells.myproj.models.TokenValidationResult;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Log4j
@Service
public class TokenValidator {

    public TokenValidationResult check(String token) {
        if (token.isEmpty()) {
            return new TokenValidationResult(-1L, HttpError.WRONG_CREDENTIALS);
        }
        String[] sp = token.split("_");
        Calendar calendar = Calendar.getInstance();
        Long userId = null;
        Long expired = null;

        try {
            expired = Long.parseLong(sp[1]);
        } catch (NumberFormatException e) {
            log.error(e);
        }
        try {
            userId = Long.parseLong(sp[0]);
        } catch (NumberFormatException e) {
            log.error(e);
        }
        if (userId == null || expired != null && calendar.getTimeInMillis() > expired) {
            return new TokenValidationResult(null, HttpError.AUTHORIZATION_IS_OUTDATED);

        } else {
            return new TokenValidationResult(userId, HttpError.EMPTY);
        }
    }
}
