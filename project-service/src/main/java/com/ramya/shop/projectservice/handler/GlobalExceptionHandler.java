
package com.ramya.shop.projectservice.handler;

import com.ramya.shop.projectservice.dto.ApiResponse;
import com.ramya.shop.projectservice.entity.Project;
import com.ramya.shop.projectservice.exception.*;
import org.aspectj.weaver.ast.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateProjectException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateProject(
            DuplicateProjectException exception) {

        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.CONFLICT.value(),
                exception.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleProjectNotFound(ProjectNotFoundException exception) {

        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(SuiteNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleSuiteNotFound(SuiteNotFoundException exception) {

        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(TestCaseNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleTestCaseNotFound(TestCaseNotFoundException exception) {

        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(DuplicateTestSuiteException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateSuite(DuplicateTestSuiteException exception) {

        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.CONFLICT.value(),
                exception.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(SuiteHasTestCasesException.class)
    public ResponseEntity<ApiResponse<Void>> handleSuiteHasTestCases(SuiteHasTestCasesException exception) {

        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.CONFLICT.value(),
                exception.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(ProjectHasResourcesException.class)
    public ResponseEntity<ApiResponse<Void>> handleProjectHasTestSuite(ProjectHasResourcesException exception) {

        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.CONFLICT.value(),
                exception.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(DuplicateProjectMemberException.class)
    public ResponseEntity<ApiResponse<Void>> handleProjectMemberDuplicates(DuplicateProjectMemberException exception) {

        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.CONFLICT.value(),
                exception.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(ProjectMemberNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleProjectMemberValidation(ProjectMemberNotFoundException exception) {

        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(
            MethodArgumentNotValidException exception) {

        String errorMessage = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                errorMessage,
                null
        );

        return ResponseEntity.badRequest().body(response);
    }

}
