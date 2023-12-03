Write-Output "Creating project..."

# Create variables
$ProjectPath = ".\target\powershell"

$SourcePath = "$ProjectPath\src"
$LibPath    = "$ProjectPath\lib"

$CommandSBT = "sbt assembly"

# Creates the output directories
mkdir $SourcePath, $LibPath -ErrorAction SilentlyContinue

# Invokes SBT to create the .jar file
Invoke-Expression $CommandSBT

# Copie the Scala code to the src directory
Copy-Item -Path "./src/main/scala" -Destination $SourcePath -Recurse -ErrorAction SilentlyContinue

# Copies the .jar file to the lib directory
Copy-Item -Path "./target/scala-3.3.1/Parser-assembly-0.0.1.jar" -Destination $LibPath

# Copies the Main.java file to the src directory
Copy-Item -Path "./src/main/java/Main.java" -Destination "$SourcePath/Main.java"


# Creates the Lexer.zip project file
# Compress-Archive `
#   -Path "$SourcePath", "$LibPath" `
#   -DestinationPath "$ProjectPath/lexer_project.zip" `
#   -CompressionLevel "Optimal" `
#   -Force

# WinRAR a -afzip -m5 -ep1 "$ProjectPath\lexer_project.zip" "$SourcePath" "$LibPath"

# # Removes the temporary directories
# Remove-Item -Path $SourcePath, $LibPath -Recurse