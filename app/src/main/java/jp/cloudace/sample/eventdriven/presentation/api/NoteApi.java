package jp.cloudace.sample.eventdriven.presentation.api;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.cloudace.sample.eventdriven.application.usecase.note.CreateNoteCommand;
import jp.cloudace.sample.eventdriven.application.usecase.note.CreateNoteUseCase;
import lombok.Data;
import lombok.Value;

@Singleton
@WebServlet(value = "/api/notes")
public class NoteApi extends HttpServlet {

    @Inject
    private Gson gson;

    @Inject
    private CreateNoteUseCase useCase;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = req.getReader().lines().collect(Collectors.joining());
        ReqBody reqBody = gson.fromJson(body, ReqBody.class);

        CreateNoteCommand command = CreateNoteCommand.builder()
                .projectId(reqBody.projectId)
                .columnId(reqBody.columnId)
                .description(reqBody.description)
                .build();
        String noteId = useCase.handle(command);

        ResBody resBody = new ResBody(reqBody.projectId, reqBody.columnId, noteId);
        resp.getWriter().println(gson.toJson(resBody));
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Data
    public static class ReqBody {
        private String projectId;
        private String columnId;
        private String description;
    }

    @Value
    public static class ResBody {
        String projectId;
        String columnId;
        String noteId;
    }

}
