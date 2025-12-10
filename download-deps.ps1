# PowerShell script to download Z3 dependencies manually

$libDir = "c:\t\gac2025\lib"

# Create lib directory if it doesn't exist
if (-not (Test-Path $libDir)) {
    New-Item -ItemType Directory -Path $libDir -Force
    Write-Host "Created lib directory: $libDir"
}

Write-Host "Downloading Z3-TurnKey JAR..."

# Z3-TurnKey JAR (includes native libraries for all platforms)
$z3TurnkeyUrl = "https://repo1.maven.org/maven2/tools/aqua/z3-turnkey/4.13.0/z3-turnkey-4.13.0.jar"
$z3TurnkeyJar = Join-Path $libDir "z3-turnkey-4.13.0.jar"

try {
    Invoke-WebRequest -Uri $z3TurnkeyUrl -OutFile $z3TurnkeyJar
    Write-Host "Downloaded: z3-turnkey-4.13.0.jar" -ForegroundColor Green
} catch {
    Write-Host "Error downloading z3-turnkey: $_" -ForegroundColor Red
    exit 1
}

Write-Host "`nAll dependencies downloaded successfully!" -ForegroundColor Green
Write-Host "`nTo compile your code, use:"
Write-Host "javac -cp `"lib\*`" --enable-preview --source 25 gac2025\day10\Day10b.java" -ForegroundColor Yellow
Write-Host "`nTo run your code, use:"
Write-Host "java -cp `"lib\*;.`" --enable-preview gac2025.day10.Day10b" -ForegroundColor Yellow
