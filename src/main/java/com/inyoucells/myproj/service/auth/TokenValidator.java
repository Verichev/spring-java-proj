package com.inyoucells.myproj.service.auth;

import com.inyoucells.myproj.models.errors.ServiceError;
import com.inyoucells.myproj.models.errors.TypicalError;

import org.springframework.stereotype.Service;

import java.util.Calendar;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class TokenValidator {

    public long parseUserId(String token) throws ServiceError {
        if (token.isEmpty()) {
            throw new ServiceError(TypicalError.WRONG_CREDENTIALS);
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
            throw new ServiceError(TypicalError.AUTHORIZATION_IS_OUTDATED);

        } else {
            return userId;
        }
    }
}
