package com.fastturtle.raahiReserve.controllers;

import com.fastturtle.raahiReserve.dtos.PaymentRequest;
import com.fastturtle.raahiReserve.dtos.PaymentRequestDTO;
import com.fastturtle.raahiReserve.enums.CardType;
import com.fastturtle.raahiReserve.enums.PaymentRefType;
import com.fastturtle.raahiReserve.helpers.CardUtils;
import com.fastturtle.raahiReserve.helpers.DateUtils;
import com.fastturtle.raahiReserve.models.*;
import com.fastturtle.raahiReserve.enums.PaymentMethod;
import com.fastturtle.raahiReserve.repositories.BankAccountRepository;
import com.fastturtle.raahiReserve.services.*;
import com.fastturtle.raahiReserve.validators.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final BookingService bookingService;
    private final BankDetailsService bankDetailsService;
    private final UserService userService;
    private final CardDetailsService cardDetailsService;
    private final BankAccountRepository bankAccountRepository;
    private User user;

    @Autowired
    public PaymentController(PaymentService paymentService, BookingService bookingService, BankDetailsService bankDetailsService, UserService userService, CardDetailsService cardDetailsService, BankAccountRepository bankAccountRepository) {
        this.paymentService = paymentService;
        this.bookingService = bookingService;
        this.bankDetailsService = bankDetailsService;
        this.userService = userService;
        this.cardDetailsService = cardDetailsService;
        this.bankAccountRepository = bankAccountRepository;
    }

    @PostMapping("/pay")
    public ResponseEntity<?> makePayment(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.makePayment(paymentRequest);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePayment(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.updatePayment(paymentRequest);
    }

    @GetMapping("/status/{bookingId}")
    public ResponseEntity<?> getPaymentStatus(@PathVariable int bookingId) {
        return paymentService.getPaymentStatus(bookingId);
    }

    @PostMapping("/fetchBookingDetails")
    public String fetchBookingDetails(@RequestParam("bookingId") Integer bookingId,
                                      @RequestParam(value = "updatedBalance", required = false) BigDecimal updatedBalance,
                                      @RequestParam(value = "isComingFromAddBalancePage", required = false) Boolean isComingFromAddBalancePage,
                                      Model model, Principal principal) {
        ResponseEntity<?> response = paymentService.checkPaymentStatusAndReturnBookingForBookingId(bookingId);
        if (response.getStatusCode() == HttpStatus.OK) {
            model.addAttribute("booking", response.getBody());
            model.addAttribute("bankDetails", bankDetailsService.getAllBankDetails());
            User loggedInUser = userService.findByUsername(principal.getName());
            model.addAttribute("walletDetails", userService.getUserWalletByEmail(loggedInUser.getEmail()));
            model.addAttribute("isBookingIdPresent", true);

            User user = userService.findByUsername(principal.getName());
            List<CardDetails> savedCards = cardDetailsService.findCardsForUser(user.getUserId());
            if(!savedCards.isEmpty()) {
                Map<CardType, List<CardDetails>> groupedCards = savedCards.stream().collect(Collectors.groupingBy(CardDetails::getCardType));
                if(groupedCards.containsKey(CardType.DEBIT)) {
                    model.addAttribute("savedDebitCards", groupedCards.get(CardType.DEBIT));
                }

                if(groupedCards.containsKey(CardType.CREDIT)) {
                    model.addAttribute("savedCreditCards", groupedCards.get(CardType.CREDIT));
                }

            }

        } else if(response.getStatusCode() == HttpStatus.NOT_FOUND) {
            String errorMessage = ((Map<String, String>)response.getBody()).get("error");
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("isBookingIdPresent", false);
        } else if(response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            model.addAttribute("successMessage", ((Map<String, String>)response.getBody()).get("warning"));
            model.addAttribute("paidAmt", ((Map<String, Float>)response.getBody()).get("paidAmt"));
            model.addAttribute("chosenPaymentMode", ((Map<String, PaymentMethod>)response.getBody()).get("chosenPaymentMode"));
            model.addAttribute("isBookingIdPresent", false);
        }

        if(user == null) {
            user = userService.findByUsername(principal.getName());
        }
        model.addAttribute("paymentModes", PaymentMethod.values());
        model.addAttribute("loggedInUserName", user.getFullName());

        if(updatedBalance != null) {
            model.addAttribute("updatedBalance", updatedBalance);
        }
        if(isComingFromAddBalancePage != null) {
            model.addAttribute("isComingFromAddBalancePage", isComingFromAddBalancePage);
        }

        return "doPayment";
    }

    @GetMapping("/doPayment")
    public String showPaymentPage(@RequestParam(value = "bookingId", required = false) Integer bookingId, Model model, Principal principal) {

        if(bookingId != null) {
            Optional<Booking> booking = bookingService.findByBookingId(bookingId);
            if (booking.isPresent()) {
                User user = userService.findByUsername(principal.getName());
                List<CardDetails> savedCards = cardDetailsService.findCardsForUser(user.getUserId());
                if(!savedCards.isEmpty()) {
                    Map<CardType, List<CardDetails>> groupedCards = savedCards.stream().collect(Collectors.groupingBy(CardDetails::getCardType));
                    if(groupedCards.containsKey(CardType.DEBIT)) {
                        model.addAttribute("savedDebitCards", groupedCards.get(CardType.DEBIT));
                    }

                    if(groupedCards.containsKey(CardType.CREDIT)) {
                        model.addAttribute("savedCreditCards", groupedCards.get(CardType.CREDIT));
                    }

                }
                String formattedBookingDate = DateUtils.formatWithOrdinalSuffix(booking.get().getBookingDate());
                String formattedTravelDate = DateUtils.formatWithOrdinalSuffix(booking.get().getTravelDate());

                Booking fetchedBooking = booking.get();
                fetchedBooking.setFormattedBookingDate(formattedBookingDate);
                fetchedBooking.setFormattedTravelDate(formattedTravelDate);
                List<BankAccount> userBankAccounts = bankAccountRepository.findBankAccountByUser_UserId(user.getUserId());

                System.out.println(userBankAccounts);

                model.addAttribute("booking", fetchedBooking);
                model.addAttribute("bankDetails", bankDetailsService.getAllBankDetails());
                model.addAttribute("walletDetails", userService.getUserWalletByEmail(user.getEmail()));
                model.addAttribute("isBookingIdPresent", true);
                model.addAttribute("paymentModes", PaymentMethod.values());
                model.addAttribute("loggedInUserName", user.getFullName());

            } else {
                if(user == null) {
                    user = userService.findByUsername(principal.getName());
                }
                model.addAttribute("loggedInUserName", user.getFullName());
                model.addAttribute("error", "Invalid Booking ID");
                return "doPayment";
            }
        }

        if(user == null) {
            user = userService.findByUsername(principal.getName());
        }
        model.addAttribute("loggedInUserName", user.getFullName());
        model.addAttribute("isBookingIdPresent", bookingId != null);
        return "doPayment";
    }

    @PostMapping("/doPayment")
    public String processPayment(@ModelAttribute PaymentRequestDTO paymentRequestDTO,
                                 RedirectAttributes redirectAttributes) {

        paymentService.processPayment(paymentRequestDTO);

        // Adding success message
        if(paymentRequestDTO.getAction().equals("completed")) {
            redirectAttributes.addFlashAttribute("successMessage", "Payment successful.");
        } else {
            redirectAttributes.addFlashAttribute("failureMessage", "Payment failed, please try the payment again after sometime");

        }

        // Redirecting to a confirmation page or back to the booking result
        return "redirect:/bookings/bookingResult?bookingId=" + paymentRequestDTO.getBookingId();
    }

    @PostMapping("/saveSelectedPaymentMode")
    public String saveSelectedPaymentMode(@RequestParam("paymentMode") String selectedPaymentMode, Model model) {
        System.out.println("Selected Payment Mode: " + selectedPaymentMode);

        model.addAttribute("selectedPaymentMode", selectedPaymentMode);

        return "resultForPaymentModeSelection";
    }

    @PostMapping("/otpValidation")
    public String showValidateOtpPage(@RequestParam("paymentModeChosen") PaymentMethod paymentMode,
                                      @RequestParam(value = "selectedBankForPayment", required = false) String selectedBankForPayment,
                                      @RequestParam(value = "cardNumber", required = false) String cardNumber,
                                      @RequestParam(value = "cardHolderName", required = false) String cardHolderName,
                                      @RequestParam(value = "expiryMonth", required = false) String expiryMonth,
                                      @RequestParam(value = "expiryYear", required = false) String expiryYear,
                                      @RequestParam(value = "cvv", required = false) String cvv,
                                      @RequestParam("bookingIdForPayment") Integer bookingId,
                                      @RequestParam(value = "selectedDebitCardID", required = false) Integer selectedDebitCardID,
                                      @RequestParam(value = "selectedCreditCardID", required = false) Integer selectedCreditCardID,
                                      Model model, Principal principal) {
        String loggedInUserName = principal.getName();
        User user = userService.findByUsername(loggedInUserName);
        String last4DigitsOfNo = user.getPhoneNumber().substring(user.getPhoneNumber().length() - 4);
        model.addAttribute("last4DigitsOfMobNo", last4DigitsOfNo);

        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();
        paymentRequestDTO.setBookingId(bookingId);
        paymentRequestDTO.setPaymentMode(paymentMode);
        String cardNumberWithSpaces = "";

        if(cardNumber != null) {
            cardNumberWithSpaces = cardNumber;
            cardNumber = cardNumber.replaceAll("\\s", "");
        }

        if(selectedBankForPayment != null) {
            model.addAttribute("selectedBankForPayment", selectedBankForPayment);
            paymentRequestDTO.setPaymentRefType(PaymentRefType.BANK);
            int bankID = bankDetailsService.finBankIDByBankName(selectedBankForPayment);
            paymentRequestDTO.setBankID(bankID);
        }

        if(cardNumber != null) {

            CardDetails cardDetails = cardDetailsService.findByCardNumber(cardNumber);

            if(cardDetails != null) {

                PaymentRequestDTO paymentRequest = new PaymentRequestDTO();
                paymentRequest.setCardHolderName(cardHolderName);
                paymentRequest.setCvv(cvv);
                paymentRequest.setExpiryMonth(Byte.valueOf(expiryMonth));
                paymentRequest.setExpiryYear(Integer.valueOf(expiryYear));

                PaymentValidatorChain validatorChain = new PaymentValidatorChain();
                validatorChain.addValidator(new ExpiryMonthValidator());
                validatorChain.addValidator(new ExpiryYearValidator());
                validatorChain.addValidator(new CVVValidator());
                validatorChain.addValidator(new CardHolderNameValidator());

                // Validate the request
                List<String> cardValidationErrorMessages = validatorChain.validate(cardDetails, paymentRequest);

                if(!cardValidationErrorMessages.isEmpty()) {
                    Optional<Booking> booking = bookingService.findByBookingId(bookingId);

                    // Add any additional data needed by the doPayment page here
                    List<CardDetails> savedCards = cardDetailsService.findCardsForUser(user.getUserId());
                    if (!savedCards.isEmpty()) {
                        Map<CardType, List<CardDetails>> groupedCards = savedCards.stream().collect(Collectors.groupingBy(CardDetails::getCardType));
                        if (groupedCards.containsKey(CardType.DEBIT)) {
                            model.addAttribute("savedDebitCards", groupedCards.get(CardType.DEBIT));
                        }
                        if (groupedCards.containsKey(CardType.CREDIT)) {
                            model.addAttribute("savedCreditCards", groupedCards.get(CardType.CREDIT));
                        }
                    }

                    String formattedBookingDate = DateUtils.formatWithOrdinalSuffix(booking.get().getBookingDate());
                    String formattedTravelDate = DateUtils.formatWithOrdinalSuffix(booking.get().getTravelDate());

                    Booking fetchedBooking = booking.get();
                    fetchedBooking.setFormattedBookingDate(formattedBookingDate);
                    fetchedBooking.setFormattedTravelDate(formattedTravelDate);

                    model.addAttribute("booking", fetchedBooking);
                    model.addAttribute("bankDetails", bankDetailsService.getAllBankDetails());
                    model.addAttribute("walletDetails", userService.getUserWalletByEmail(user.getEmail()));
                    model.addAttribute("loggedInUserName", user.getFullName());
                    model.addAttribute("isBookingIdPresent", true);
                    model.addAttribute("paymentModes", PaymentMethod.values());
                    model.addAttribute("chosenModeBeforeInvalidCardDetail", paymentMode.name());

                    String errorMessageHead = "The card you entered matches one you've used before, but some details seem incorrect. Please double-check the following:";
                    model.addAttribute("cardValidationErrorMessageHeading", errorMessageHead);
                    model.addAttribute("cardValidationErrorMessages", cardValidationErrorMessages);
                    model.addAttribute("alreadySavedCardNo", cardNumberWithSpaces);
                    model.addAttribute("invalidCardHolder", cardHolderName);
                    model.addAttribute("invalidExpiryDate", expiryMonth + "/" + expiryYear);
                    model.addAttribute("paymentModeChosen", paymentMode);

                    return "doPayment";
                }
            }

            String cardCompany = CardUtils.getCardCompany(cardNumber);

            if(!cardCompany.equals("Invalid Card")) {
                paymentRequestDTO.setPaymentRefType(PaymentRefType.CARD);
                paymentRequestDTO.setCardNo(cardNumber);
                paymentRequestDTO.setCardCompany(CardUtils.getCardCompany(cardNumber));
                paymentRequestDTO.setCardHolderName(cardHolderName);
                paymentRequestDTO.setExpiryMonth(Byte.valueOf(expiryMonth));
                paymentRequestDTO.setExpiryYear(Integer.valueOf(expiryYear));
                paymentRequestDTO.setCvv(cvv);
                paymentRequestDTO.setUserID(user.getUserId());
            }
        } else if(selectedDebitCardID != null) {
            CardDetails selectedCardDetails = cardDetailsService.findByID(selectedDebitCardID);

            paymentRequestDTO.setPaymentRefType(PaymentRefType.CARD);
            paymentRequestDTO.setCardNo(selectedCardDetails.getCardNumber());
            paymentRequestDTO.setCardCompany(selectedCardDetails.getCardCompany());

        } else if(selectedCreditCardID != null) {
            CardDetails selectedCardDetails = cardDetailsService.findByID(selectedCreditCardID);

            paymentRequestDTO.setPaymentRefType(PaymentRefType.CARD);
            paymentRequestDTO.setCardNo(selectedCardDetails.getCardNumber());
            paymentRequestDTO.setCardCompany(selectedCardDetails.getCardCompany());


        } else if(paymentMode == PaymentMethod.WALLET) {
            paymentRequestDTO.setPaymentRefType(PaymentRefType.USER);
            paymentRequestDTO.setUserID(user.getUserId());
        }

        paymentRequestDTO.setAction("completed");
        model.addAttribute("paymentRequestDTO", paymentRequestDTO);
        return "securePaymentGateway";
    }
	
	//TODO 1. Add Modify booking option
	//2.User can add or remove passengers from existing booking
	//3. Add a bank account entity which should be linked to netbanking, and debit cards.
	//4. Link every debit card with a bank account, and every bank account should have a bank balance, so that it can be deducted when booking is done.
	//5. Add bank balance to netbanking as well
	//6. Add credit limit and available credit limit to credit cards, also, for newly added credit cards, assign some default credit limit, depending on card company.
	//7. Also add option to cancel a booking, if its payment is not yet done. And, it should be cancellable only 48 hours before travelling date and time.
	//8. If feasible, add a job scheduler, which cancels a booking automatically, if its payment is not done 24 hours before travelling.
    //9. Change the booking status from created to completed, if all the passengers are travelled in it.
    //10. When a booking is cancelled, vacate all the seats occupied by passengers in it.
    //11. Add UI changes to payment page, to show only those banks in netbanking, in which user has bank account.

}
