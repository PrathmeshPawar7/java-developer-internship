# Employee Payroll System (Java, Console-Based)

A layered, console-based Employee Payroll System built in core Java — an
interview-ready internship project demonstrating OOP, a real salary
calculation pipeline, progressive tax-slab logic, and clean architecture
that maps directly onto Spring Boot's Controller -> Service -> Repository
pattern.

## Features

- **Employee records** — add, view, list, and remove employees with
  designation, department, and basic salary.
- **Salary calculation** — allowances (HRA, DA, Special Allowance) are
  derived from basic salary using configurable percentages, producing the
  gross salary.
- **Tax & deductions**:
  - **Provident Fund (PF)** — 12% of basic, capped at the statutory monthly
    ceiling.
  - **Professional Tax** — flat monthly tax, waived below a gross-salary
    threshold.
  - **Income Tax (TDS)** — calculated with a genuine **progressive slab
    system** (`TaxCalculator`), not a flat percentage: the monthly gross is
    annualized, tax is computed slab-by-slab, then divided back to a
    monthly deduction.
- **Payslip generation** — produces a fully formatted, printable salary
  slip (earnings, deductions, net pay) and blocks duplicate generation for
  the same employee/month/year.
- **Payroll history** — view all payslips for one employee or a bank-wide
  (company-wide) summary of every payslip generated.
- **Custom checked exceptions** — `EmployeeNotFoundException`,
  `InvalidSalaryException`, `PayrollAlreadyGeneratedException` for
  explicit, testable error handling instead of null checks.

## Architecture

```
Main.java              -> Console UI / entry point (dependency wiring)
model/                 -> Employee, Department, SalarySlip (POJOs)
dao/                    -> EmployeeDAO, SalarySlipDAO (interfaces)
                           + in-memory Impl classes (HashMap-backed)
service/                -> PayrollService (interface) + PayrollServiceImpl
                           (salary calculation pipeline, orchestration)
exception/              -> Custom checked exceptions
util/                   -> IdGenerator, PayrollConstants, TaxCalculator
```

This mirrors a real-world layered enterprise design:

- **Model layer** — plain data objects. `SalarySlip` is immutable once
  created, since a generated payslip is a historical financial record that
  should never be edited after the fact — a correction becomes a new slip.
- **DAO layer** — persistence abstraction. The in-memory `HashMap` stores
  can be swapped for JDBC/Spring Data JPA repositories later without
  touching the service or UI layers.
- **Service layer** — owns the calculation pipeline: basic -> allowances ->
  gross -> deductions -> net. `TaxCalculator` and `PayrollConstants` are
  deliberately separated from the service class (single-responsibility) so
  the slab table or percentages can change without touching orchestration
  logic.
- **UI layer** (`Main`) — purely responsible for I/O and delegates all
  logic to `PayrollService`.

## How to Run

```bash
cd src
javac Main.java model/*.java dao/*.java service/*.java exception/*.java util/*.java -d ../out
java -cp ../out Main
```

The app seeds two sample employees on startup so you can generate a
payslip immediately (menu option 5).

## Sample Payslip Output

```
========================================================
                    SALARY SLIP
========================================================
Slip ID       : 1
Employee ID   : 1001
Employee Name : Prathmesh Pawar
Designation   : Java Backend Developer
Pay Period    : AUGUST 2026
--------------------------------------------------------
EARNINGS                              AMOUNT (Rs.)
Basic Salary                              45000.00
HRA                                       18000.00
DA                                         4500.00
Special Allowance                          6750.00
--------------------------------------------------------
GROSS SALARY                              74250.00
--------------------------------------------------------
DEDUCTIONS                             AMOUNT (Rs.)
Provident Fund (PF)                        1800.00
Professional Tax                            200.00
Income Tax (TDS)                           3675.00
--------------------------------------------------------
TOTAL DEDUCTIONS                           5675.00
========================================================
NET SALARY                                68575.00
========================================================
Generated On  : 2026-08-14
```

## Possible Extensions (great talking points in an interview)

- Swap the in-memory DAO implementations for Spring Data JPA + MySQL.
- Expose the same service layer via REST controllers (Spring Boot).
- Export payslips as PDF (Apache PDFBox / iText) instead of console text.
- Add attendance/leave integration to prorate salary for partial months.
- Add configurable tax regimes (old vs new) as a strategy pattern.
- Add Spring Security + JWT so employees can only view their own payslips.
