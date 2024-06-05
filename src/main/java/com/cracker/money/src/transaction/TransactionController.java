package com.cracker.money.src.transaction;

import com.cracker.money.common.response.BaseResponse;
import com.cracker.money.src.transaction.model.TransactionResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/app/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * Get list of transactions for a specific user API
     * [GET] /app/v1/transactions/user/:userId
     * @return BaseResponse<GetTransactionRes>
     */
    @Operation(summary = "Transactions of a specific user",
            description = "Get list of transactions for a specific user",
            tags = { "Transaction Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = TransactionResDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/user/{userId}")
    public BaseResponse<List<TransactionResDto>> getTransactionsByUser(@Validated @PathVariable("userId") Long userId) {
        List<TransactionResDto> getTransactionRes = transactionService.getTransactionsByUsers(userId);
        return new BaseResponse<>(getTransactionRes);
    }


}
