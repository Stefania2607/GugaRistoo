package controller.grafico;

import javax.servlet.http.*;
import java.io.IOException;

public abstract class BaseController extends HttpServlet {

    protected void safeRedirect(HttpServletRequest request,
                                HttpServletResponse response,
                                String targetUrl,
                                String logMessage) {
        try {
            response.sendRedirect(response.encodeRedirectURL(targetUrl));
        } catch (IOException e) {
            log(logMessage + " | target=" + targetUrl, e);
            safeSendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (RuntimeException e) {
            log("Errore runtime durante redirect: " + targetUrl, e);
            safeSendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void safeForward(HttpServletRequest request,
                               HttpServletResponse response,
                               String jspPath,
                               String logMessage) {
        try {
            request.getRequestDispatcher(jspPath).forward(request, response);
        } catch (Exception e) {
            log(logMessage + " | jsp=" + jspPath, e);
            safeSendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void safeSendError(HttpServletResponse response, int statusCode) {
        if (response.isCommitted()) return;
        try {
            response.sendError(statusCode);
        } catch (IOException | RuntimeException e) {
            log("Impossibile inviare errore HTTP " + statusCode, e);
        }
    }
}
