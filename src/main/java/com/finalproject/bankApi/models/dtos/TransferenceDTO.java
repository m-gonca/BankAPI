package com.finalproject.bankApi.models.dtos;


import java.math.BigDecimal;

public class TransferenceDTO {
    private BigDecimal amount;
    private Long sendAccountId;
    private String ownerName;
    private Long receiveAccountId;

    public TransferenceDTO(){}
    public TransferenceDTO(BigDecimal amount, Long sendAccountId, String ownerName, Long receiveAccountId) {
        this.amount = amount;
        this.sendAccountId = sendAccountId;
        this.ownerName = ownerName;
        this.receiveAccountId = receiveAccountId;
    }

    public TransferenceDTO(BigDecimal amount, Long sendAccountId, Long receiveAccountId) {
        this.amount = amount;
        this.sendAccountId = sendAccountId;
        this.receiveAccountId = receiveAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getSendAccountId() {
        return sendAccountId;
    }

    public void setSendAccountId(Long sendAccountId) {
        this.sendAccountId = sendAccountId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Long getReceiveAccountId() {
        return receiveAccountId;
    }

    public void setReceiveAccountId(Long receiveAccountId) {
        this.receiveAccountId = receiveAccountId;
    }
}
