package com.devicehive.auth.websockets;


import com.devicehive.auth.HivePrincipal;
import com.devicehive.auth.HiveRoles;
import com.devicehive.auth.HiveSecurityContext;
import com.devicehive.exceptions.HiveException;
import com.devicehive.websockets.handlers.annotations.WebsocketController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;
import java.lang.reflect.Method;

@Interceptor
@WebsocketController
@Priority(Interceptor.Priority.APPLICATION + 200)
public class AuthorizationInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    @Inject
    private HiveSecurityContext hiveSecurityContext;

    @AroundInvoke
    public Object authorize(InvocationContext context) throws Exception {
        Method method = context.getMethod();
        boolean allowed = false;
        HivePrincipal principal = hiveSecurityContext.getHivePrincipal();
        if (method.isAnnotationPresent(RolesAllowed.class)) {
            RolesAllowed rolesAllowed = method.getAnnotation(RolesAllowed.class);
            String[] roles = rolesAllowed.value();
            for (String role : roles) {
                switch (role) {
                    case HiveRoles.ADMIN:
                        allowed = allowed ||
                                  (principal != null && principal.getUser() != null && principal.getUser().isAdmin());
                        break;
                    case HiveRoles.CLIENT:
                        allowed = allowed || (principal != null && principal.getUser() != null);
                        break;
                    case HiveRoles.DEVICE:
                        allowed = allowed || (principal != null && principal.getDevice() != null);
                        break;
                    case HiveRoles.KEY:
                        allowed = allowed || (principal != null && principal.getKey() != null);
                        break;
                    default:
                }
            }
        } else {
            allowed = method.isAnnotationPresent(PermitAll.class);
        }
        if (!allowed) {
            if (principal != null) {
                LOGGER.error("Authorization failed: user {}, device {}, accessKey {}", principal.getUser(), principal.getDevice(), principal.getKey());
            } else {
                LOGGER.error("Authorization failed: principal is null");
            }
            throw new HiveException(Response.Status.FORBIDDEN.getReasonPhrase(),
                                    Response.Status.FORBIDDEN.getStatusCode());
        }
        return context.proceed();
    }

}
