# Set the path to the input file
$inputFile = "LPP.g4"

# Set the path to the output file
$outputFile = "LPPGrammar.bnf"

# Read the input file
$content = Get-Content $inputFile | Select-Object -Skip 1

# Replace all occurrences of "//" with "#"
$content = $content -replace "//", "#"

# Write the modified content to the output file
$content | Out-File $outputFile