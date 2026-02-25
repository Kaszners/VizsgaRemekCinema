package hu.nyirszikszi.vizsgaremek.cinema.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@NoArgsConstructor(access = AccessLevel. PRIVATE)
public class SecurityUtil {

    public static String getCurrentUsername(){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()){
            return null;
        }
        return authentication.getName();
    }

}
