package com.poly.app.infrastructure.constant;

public enum StatusBill {
    DA_HUY(1),
    CHO_XAC_NHAN(2),
    DA_XAC_NHAN(3),
    DANG_VAN_CHUYEN(4),
    DA_GIAO_HANG(5),
    DA_THANH_TOAN(6),
    CHO_THANH_TOAN(7),
    HOAN_THANH(8),
    TAO_DON_HANG(9),
    TRA_HANG(10), //9
    KHONG_TON_TAI(11), //10
    HUY_YEU_CAU_TRA_HANG(12),
    TU_CHOI_TRA_HANG(13);


    private final int code;

    StatusBill(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static StatusBill fromCode(int code) {
        for (StatusBill status : StatusBill.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status code: " + code);
    }


    }
