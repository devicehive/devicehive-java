package com.devicehive.controller;


import com.devicehive.auth.HiveRoles;
import com.devicehive.configuration.Constants;
import com.devicehive.configuration.Messages;
import com.devicehive.controller.converters.SortOrder;
import com.devicehive.controller.util.ResponseFactory;
import com.devicehive.exceptions.HiveException;
import com.devicehive.json.strategies.JsonPolicyApply;
import com.devicehive.model.AccessType;
import com.devicehive.model.ErrorResponse;
import com.devicehive.model.OAuthGrant;
import com.devicehive.model.Type;
import com.devicehive.model.User;
import com.devicehive.model.updates.OAuthGrantUpdate;
import com.devicehive.service.OAuthGrantService;
import com.devicehive.service.UserService;
import com.devicehive.util.AsynchronousExecutor;
import com.devicehive.util.LogExecutionTime;
import com.devicehive.util.ThreadLocalVariablesKeeper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.CompletionCallback;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.List;

import static com.devicehive.configuration.Constants.*;
import static com.devicehive.json.strategies.JsonPolicyDef.Policy.OAUTH_GRANT_LISTED;
import static com.devicehive.json.strategies.JsonPolicyDef.Policy.OAUTH_GRANT_LISTED_ADMIN;
import static com.devicehive.json.strategies.JsonPolicyDef.Policy.OAUTH_GRANT_PUBLISHED;
import static com.devicehive.json.strategies.JsonPolicyDef.Policy.OAUTH_GRANT_SUBMITTED_CODE;
import static com.devicehive.json.strategies.JsonPolicyDef.Policy.OAUTH_GRANT_SUBMITTED_TOKEN;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Path("/user/{userId}/oauth/grant")
@LogExecutionTime
public class OAuthGrantController {

    private static final Logger logger = LoggerFactory.getLogger(OAuthGrantController.class);
    @EJB
    private OAuthGrantService grantService;
    @EJB
    private UserService userService;
    @EJB
    private AsynchronousExecutor executor;

    @GET
    @RolesAllowed({HiveRoles.ADMIN, HiveRoles.CLIENT})
    public void list(@PathParam(USER_ID) final String userId,
                     @QueryParam(START) final Timestamp start,
                     @QueryParam(END) final Timestamp end,
                     @QueryParam(CLIENT_OAUTH_ID) final String clientOAuthId,
                     @QueryParam(TYPE) final String type,
                     @QueryParam(SCOPE) final String scope,
                     @QueryParam(REDIRECT_URI) final String redirectUri,
                     @QueryParam(ACCESS_TYPE) final String accessType,
                     @QueryParam(SORT_FIELD) @DefaultValue(TIMESTAMP) final String sortField,
                     @QueryParam(SORT_ORDER) @SortOrder final Boolean sortOrder,
                     @QueryParam(TAKE) final Integer take,
                     @QueryParam(SKIP) final Integer skip,
                     @Suspended final AsyncResponse asyncResponse) {
        logger.debug("OAuthGrant: list requested. User id: {}, start: {}, end: {}, clientOAuthID: {}, type: {}, " +
                "scope: {}, redirectURI: {}, accessType: {}, sortField: {}, sortOrder: {}, take: {}, skip: {}",
                userId, start, end, clientOAuthId, type, scope, redirectUri, accessType, sortField, sortOrder, take,
                skip);
        asyncResponse.register(new CompletionCallback() {
            @Override
            public void onComplete(Throwable throwable) {
                logger.debug(
                        "OAuthGrant: list proceed successfully. User id: {}, start: {}, end: {}, clientOAuthID: {}, " +
                                "type: {}, scope: {}, redirectURI: {}, accessType: {}, sortField: {}, sortOrder: {}, take: {}, skip: {}",
                        userId, start, end, clientOAuthId, type, scope, redirectUri, accessType, sortField, sortOrder, take,
                        skip);
            }
        });
        final User user = getUser(userId);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try{
                if (!sortField.equalsIgnoreCase(TIMESTAMP)) {
                    asyncResponse.resume(ResponseFactory.response(BAD_REQUEST,
                            new ErrorResponse(BAD_REQUEST.getStatusCode(), Messages.INVALID_REQUEST_PARAMETERS)));
                }
                final String sortFieldLower = sortField.toLowerCase();
                final boolean sortOrderRes = sortOrder == null ? true : sortOrder;

                List<OAuthGrant> result = grantService.list(user, start, end, clientOAuthId,
                        type == null ? null : Type.forName(type).ordinal(), scope,
                        redirectUri, accessType == null ? null : AccessType.forName(accessType).ordinal(), sortFieldLower,
                        sortOrderRes, take, skip);

                if (user.isAdmin()) {
                    asyncResponse.resume(ResponseFactory.response(OK, result, OAUTH_GRANT_LISTED_ADMIN));
                }
                asyncResponse.resume(ResponseFactory.response(OK, result, OAUTH_GRANT_LISTED));
                } catch (Exception e){
                    asyncResponse.resume(e);
                }
            }
        }) ;

    }

    @GET
    @Path("/{id}")
    @RolesAllowed({HiveRoles.ADMIN, HiveRoles.CLIENT})
    public Response get(@PathParam(USER_ID) String userId,
                        @PathParam(ID) long grantId) {
        logger.debug("OAuthGrant: get requested. User id: {}, grant id: {}", userId, grantId);
        User user = getUser(userId);
        OAuthGrant grant = grantService.get(user, grantId);
        if (grant == null) {
            throw new HiveException(String.format(Messages.GRANT_NOT_FOUND, grantId), NOT_FOUND.getStatusCode());
        }
        logger.debug("OAuthGrant: proceed successfully. User id: {}, grant id: {}", userId, grantId);
        if (user.isAdmin()) {
            return ResponseFactory.response(OK, grant, OAUTH_GRANT_LISTED_ADMIN);
        }
        return ResponseFactory.response(OK, grant, OAUTH_GRANT_LISTED);
    }

    @POST
    @RolesAllowed({HiveRoles.ADMIN, HiveRoles.CLIENT})
    public Response insert(@PathParam(USER_ID) String userId,
                           @JsonPolicyApply(OAUTH_GRANT_PUBLISHED) OAuthGrant grant) {
        logger.debug("OAuthGrant: insert requested. User id: {}, grant: {}", userId, grant);
        User user = getUser(userId);
        grantService.save(grant, user);
        logger.debug("OAuthGrant: insert proceed successfully. User id: {}, grant: {}", userId, grant);
        if (grant.getType().equals(Type.TOKEN)) {
            return ResponseFactory.response(CREATED, grant, OAUTH_GRANT_SUBMITTED_TOKEN);
        } else {
            return ResponseFactory.response(CREATED, grant, OAUTH_GRANT_SUBMITTED_CODE);
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({HiveRoles.ADMIN, HiveRoles.CLIENT})
    public Response update(@PathParam(USER_ID) String userId,
                           @PathParam(ID) Long grantId,
                           @JsonPolicyApply(OAUTH_GRANT_PUBLISHED) OAuthGrantUpdate grant) {
        logger.debug("OAuthGrant: update requested. User id: {}, grant id: {}", userId, grantId);
        User user = getUser(userId);
        OAuthGrant updated = grantService.update(user, grantId, grant);
        if (updated == null) {
            throw new HiveException(String.format(Messages.GRANT_NOT_FOUND, grantId), NOT_FOUND.getStatusCode());
        }
        logger.debug("OAuthGrant: update proceed successfully. User id: {}, grant id: {}", userId, grantId);
        if (updated.getType().equals(Type.TOKEN)) {
            return ResponseFactory.response(OK, updated, OAUTH_GRANT_SUBMITTED_TOKEN);
        } else {
            return ResponseFactory.response(OK, updated, OAUTH_GRANT_SUBMITTED_CODE);
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({HiveRoles.ADMIN, HiveRoles.CLIENT})
    public Response delete(@PathParam(USER_ID) String userId,
                           @PathParam(ID) Long grantId) {
        logger.debug("OAuthGrant: delete requested. User id: {}, grant id: {}", userId, grantId);
        User user = getUser(userId);
        grantService.delete(user, grantId);
        logger.debug("OAuthGrant: delete proceed successfully. User id: {}, grant id: {}", userId, grantId);
        return ResponseFactory.response(NO_CONTENT);
    }

    private User getUser(String userId) {
        User current = ThreadLocalVariablesKeeper.getPrincipal().getUser();
        if (userId.equalsIgnoreCase(Constants.CURRENT_USER)) {
            return current;
        }
        if (StringUtils.isNumeric(userId)) {
            Long id = Long.parseLong(userId);
            if (current.getId().equals(id)) {
                return current;
            } else if (current.isAdmin()) {
                User result = userService.findById(id);
                if (result == null) {
                    throw new HiveException(String.format(Messages.USER_NOT_FOUND, userId), NOT_FOUND.getStatusCode());
                }
                return result;
            }
            throw new HiveException(Messages.UNAUTHORIZED_REASON_PHRASE, UNAUTHORIZED.getStatusCode());
        }
        throw new HiveException(String.format(Messages.BAD_USER_IDENTIFIER, userId), BAD_REQUEST.getStatusCode());
    }

}