package city.commerce.model.api;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-31
 */
public enum StatusCode {
    OK(Status.OK),

    AuthorityAbsent(Status.Unauthorized),

    AuthorityExpired(Status.Unauthorized),

    AuthenticationInvalidToken(Status.BadRequest),

    AuthenticationInvalidSign(Status.BadRequest),

    AuthenticationInvalidName(Status.BadRequest),

    AuthenticationInvalidPassword(Status.BadRequest),

    AuthenticationSignInRequired(Status.BadRequest),

    AuthenticationPermissionDenied(Status.Unauthorized),

    RequestParameterInvalid(Status.BadRequest),

    ResourceExisted(Status.BadRequest),

    ResourceNotFound(Status.NotFound),

    ServerUnknownError(Status.ServerError),

    CategoryTreeCircled(Status.ServerError),

    DivisionTreeCircled(Status.ServerError)
    ;

    private int status;

    StatusCode(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return name();
    }
}
