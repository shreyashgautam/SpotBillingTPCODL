package com.example.loginpage.model

data class SpotBillingResponse(
    val status: Boolean,

    val status_code: Long,
    val message: String,

    val user_id: String,

    val server_date_time: String,
    val software_version_no: String,
    val response: List<Response>,
)

data class Response(

    val header_details: List<HeaderDetail>,
)

data class HeaderDetail(
    val id: String,
    val installation: String,
    val address1: String,
    val address2: String?,
    val aifi: String,
    val amountPayable: String,
    val arrears: String,
    val asd: String,
    val asdaa: String,
    val averageKwh: String,
    val avgUnitBilled: String,
    val bank: Any?,
    val billBasis: String,
    val billMonth: String,
    val billNo: Any?,
    val billYear: String,
    val refDate: String,
    val billPrintHeader: Any?,
    val billPrintFooter: String?,
    val bpEndDate: Any?,
    val bpStartDate: Any?,
    val buildingCode: Any?,
    val buildingDesc: Any?,
    val ca: String,
    val capturedMobile: Any?,
    val fcSlab: String,
    val cblass: Any?,
    val chqdt: Any?,
    val chqno: Any?,
    val consumerOwned: String,
    val consumerType: Any?,
    val contactReasonId: Any?,
    val crAdj: String,
    val currentBillTotal: String,
    val dataDownloadFlag: String,
    val dbAdj: String,
    val div: String?,
    val doExpiry: Any?,
    val doGenerated: Any?,
    val dps: String,
    val dueDate: Any?,
    val ec: String,
    val ecsLimt: String,
    val ecsValidityPeriod: Any?,
    val ed: String,
    val edExempt: Any?,
    val edRbt: String,
    val eltStts: Any?,
    val flag: Any?,
    val gpsLatitude: Any?,
    val gpsStringitude: Any?,
    val gstRelevant1: Any?,
    val gstRelevant2: Any?,
    val gstRelevant3: Any?,
    val gstRelevant4: String?,
    val hlMonths: Any?,
    val hostelRbt: Any?,
    val insertDate: String,
    val insertTime: Any?,
    val invoiceNo: Any?,
    val lastOkDate: String,
    val lastPaidAmt: String,
    val lastPaidDate: String?,
    val lastPymtRcpt: Any?,
    val legacyAccountNo: String?,
    val legacyAccountNo2: String?,
    val meterHeightId: Any?,
    val meterLoca: Any?,
    val meterMake: String?,
    val meterRent: String,
    val meterTypeId: String?,
    val miscCharges: String,
    val mmfc: String,
    val moveInDate: String,
    val mrRemarkDet: Any?,
    val mrentCharged: String,
    val mrreason: Any?,
    val mru: String,
    val name: String,
    val newMeterNo: Any?,
    val newMtrFlg: Any?,
    val notToBillAfter: String,
    val notinuseFlgEnddate: Any?,
    val oldMtrCorFlg: Any?,
    val osbillDate: Any?,
    val otherFlgs: Any?,
    val paperPasteById: Any?,
    val phone1: String?,
    val phone2: String?,
    val photoUploadFlg: Any?,
    val poleNo: Any?,
    val portion: String,
    val ppac: String,
    val ppi: Any?,
    val presentBillType: Any?,
    val presentBillUnits: String,
    val presentMeterStatus: Any?,
    val presentReadingDate: String?,
    val presentReadingRemark: Any?,
    val presentReadingTime: Any?,
    val prevBillEndDate: String?,
    val prevBillRemark: Any?,
    val prevBillType: String,
    val prevBillUnits: String,
    val prevProvAmt: String,
    val previousBilledProvUnit: Any?,
    val progressionState: Any?,
    val prvArr: Any?,
    val prvBilledAmt: Any?,
    val rateCategory: String,
    val rcptamt: String,
    val rcptno: Any?,
    val readOnly: String,
    val reasonCdId: Any?,
    val reasonDcId: Any?,
    val reasonEnId: Any?,
    val reasonMtrStuckId: Any?,
    val reasonNvId: Any?,
    val reasonPlId: Any?,
    val rebate: String,
    val roundOff: String,
    val sanLoad: String,
    val sanLoadEffectiveDate: Any?,
    val sanLoadUnits: String,
    val sbmBillNo: String?,
    val scheduleMeterReadDate: String,
    val sdi: String,
    val sealStsId: Any?,
    val sealStts: Any?,
    val secDepositAmt: String,
    val section: String?,
    val senderMobile: Any?,
    val seq: Any?,
    val specialRem: Any?,
    val stopPaprBl: Any?,
    val subDiv: String?,
    val subSeq: Any?,
    val supplySourceId: Any?,
    val supplyStatusId: Any?,
    val supplyTypFlg: Any?,
    val tdDate: String?,
    val tdFlag: String?,
    val transType: Any?,
    val typesObstacleId: Any?,
    val ulf: String,
    val unsafeCond: Any?,
    val updateDate: String,
    val updateTime: Any?,
    val usage: Any?,
    val usageId: Any?,
    val walkingSeqChk: Any?,
    val refMrDate: String,
    val rural: String,
    val swJlDhFl: String,
    val rcRdLoad: String,
    val ulfMdi: String,
    val dps5: String,
    val dpsBilled: Any?,
    val dpsLvd: String,
    val provPpiAmt: String,
    val provEd: String,
    val reverseFlag: Any?,
    val adjBill: String?,
    val officeCode: String?,
    val divisionCode: String?,
    val subdivisionCode: String?,
    val prvScheduleMtrReadDt: String?,
    val slabDays: String,
    val followUpCount: Long,
    val billStopFlag: Any?,
    val dkjdomflg: Any?,
    val mmiAssignedId: String?,
    val mmiSequenceId: Long?,
    val mmiJobStatus: String?,
    val mmiUpdateOn: Long?,
    val mmiAccountId: String?,
    val mmiEta: Any?,
    val mmiStatus: Long,
    val mmiCaNotificationTaskId: Long,
    val lat: Any?,
    val lng: Any?,
    val child_data_List: List<ChildDataList>,
    val other_charges_list: List<Any?>,
)

data class ChildDataList(
    val ablbelnr: String,
    val billMonth: String,
    val billYear: String,
    val billedMd: Any?,
    val consumptionOldMeter: String,
    val equipmentNo: String,
    val installation: String,
    val lastOkRdng: String,
    val meterCondition: String,
    val meterInstallDate: String,
    val meterNo: String,
    val meterRemovedOn: Any?,
    val meterTyp: String,
    val noOfReg: String,
    val mf: String,
    val mrreason: Any?,
    val newMeterFlg: String?,
    val noOfDigits: String,
    val presentMeterReading: String?,
    val prevMtrRead: String,
    val prevReadDate: String,
    val previousMd: Any?,
    val prsMd: Any?,
    val registerCode: String,
    val reverseFlag: Any?,
    val transType: Any?,
    val scheduleMeterReadDate: String,
)
