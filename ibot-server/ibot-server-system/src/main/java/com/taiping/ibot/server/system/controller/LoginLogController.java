package com.taiping.ibot.server.system.controller;


import com.taiping.ibot.common.entity.IBotResponse;
import com.taiping.ibot.common.entity.QueryRequest;
import com.taiping.ibot.common.entity.system.LoginLog;
import com.taiping.ibot.common.utils.IBotUtil;
import com.taiping.ibot.server.system.annotation.ControllerEndpoint;
import com.taiping.ibot.server.system.service.ILoginLogService;
import com.wuwenze.poi.ExcelKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author MrBird
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("loginLog")
public class LoginLogController {

    private final ILoginLogService loginLogService;

    @GetMapping
    public IBotResponse loginLogList(LoginLog loginLog, QueryRequest request) {
        Map<String, Object> dataTable = IBotUtil.getDataTable(this.loginLogService.findLoginLogs(loginLog, request));
        return new IBotResponse().data(dataTable);
    }

    @GetMapping("currentUser")
    public IBotResponse getUserLastSevenLoginLogs() {
        String currentUsername = IBotUtil.getCurrentUsername();
        List<LoginLog> userLastSevenLoginLogs = this.loginLogService.findUserLastSevenLoginLogs(currentUsername);
        return new IBotResponse().data(userLastSevenLoginLogs);
    }

    @DeleteMapping("{ids}")
    @PreAuthorize("hasAuthority('loginlog:delete')")
    @ControllerEndpoint(operation = "删除登录日志", exceptionMessage = "删除登录日志失败")
    public void deleteLogs(@NotBlank(message = "{required}") @PathVariable String ids) {
        String[] loginLogIds = ids.split(",");
        this.loginLogService.deleteLoginLogs(loginLogIds);
    }

    @PostMapping("excel")
    @PreAuthorize("hasAuthority('loginlog:export')")
    @ControllerEndpoint(operation = "导出登录日志数据", exceptionMessage = "导出Excel失败")
    public void export(QueryRequest request, LoginLog loginLog, HttpServletResponse response) {
        List<LoginLog> loginLogs = this.loginLogService.findLoginLogs(loginLog, request).getRecords();
        ExcelKit.$Export(LoginLog.class, response).downXlsx(loginLogs, false);
    }
}
