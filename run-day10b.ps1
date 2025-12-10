# PowerShell script to compile and run Day10b with Z3 solver

Write-Host "Day 10 Part B - Factory Joltage Solver with Z3" -ForegroundColor Cyan

# Check if lib directory and Z3 JAR exist
if (-not (Test-Path "lib\z3-turnkey-4.13.0.jar")) {
    Write-Host "`nZ3 library not found. Running download script..." -ForegroundColor Yellow
    .\download-deps.ps1
    if ($LASTEXITCODE -ne 0) {
        Write-Host "`nFailed to download dependencies!" -ForegroundColor Red
        exit 1
    }
}

Write-Host "`nCompiling Java files..." -ForegroundColor Yellow
javac -cp "lib\*" --enable-preview --source 25 gac2025\Base.java gac2025\day10\Day10b.java

if ($LASTEXITCODE -ne 0) {
    Write-Host "`nCompilation failed!" -ForegroundColor Red
    exit 1
}

Write-Host "Compilation successful!" -ForegroundColor Green

Write-Host "`nRunning solution..." -ForegroundColor Yellow
java -cp "lib\*;." --enable-preview gac2025.day10.Day10b

Write-Host "Execution complete!" -ForegroundColor Green
