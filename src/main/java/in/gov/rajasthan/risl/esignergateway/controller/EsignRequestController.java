package in.gov.rajasthan.risl.esignergateway.controller;

import in.gov.rajasthan.risl.esignergateway.dto.request.MerchantRequestDTO;
import in.gov.rajasthan.risl.esignergateway.enums.FlowType;
import in.gov.rajasthan.risl.esignergateway.model.ProcessingResult;
import in.gov.rajasthan.risl.esignergateway.service.EsignRedirectionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/esign")
@RequiredArgsConstructor
public class EsignRequestController {

    private final EsignRedirectionService esignRedirectionService;

    @PostMapping(value = "/redirect", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object receiveRequest(
            @Valid @ModelAttribute MerchantRequestDTO requestDto,
            BindingResult bindingResult,
            HttpServletRequest httpServletRequest) {

        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                    .toList();

            log.warn("structural validation failed: {}", errors);

            Map<String, Object> errorBody = new LinkedHashMap<>();
            errorBody.put("success", false);
            errorBody.put("errorCode", "VALIDATION_ERROR");
            errorBody.put("errors", errors);
            return ResponseEntity.badRequest().body(errorBody);
        }

        ProcessingResult result = esignRedirectionService.processRequest(requestDto, httpServletRequest);

        if (result.getFlowType() == FlowType.REDIRECT) {
            ModelAndView modelAndView = new ModelAndView("auto-submit-redirect");
            modelAndView.addObject("targetUrl", result.getRedirectUrl());
            modelAndView.addObject("params", result.getRedirectParams());
            return modelAndView;
        }

        // UI and PREVIEW flow types don't have a page to render yet -
        // return the decision as JSON so it's visible/testable from Postman.
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("success", result.isSuccess());
        body.put("requestId", result.getRequestId());
        body.put("flowType", result.getFlowType());
        body.put("redirectUrl", result.getRedirectUrl());
        return ResponseEntity.ok(body);
    }
}
