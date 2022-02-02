package com.inyoucells.myproj.service.auth;

import com.inyoucells.myproj.models.errors.HttpErrorMessage;
import com.inyoucells.myproj.models.errors.ServiceError;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Log4j
@Service
public class TokenValidator {

    public long parseUserId(String token) throws ServiceError {
        if (token.isEmpty()) {
            throw new ServiceError(HttpStatus.UNAUTHORIZED, HttpErrorMessage.WRONG_CREDENTIALS);
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
            throw new ServiceError(HttpStatus.UNAUTHORIZED, HttpErrorMessage.AUTHORIZATION_IS_OUTDATED);

        } else {
            return userId;
        }
    }
}
