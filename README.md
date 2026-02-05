Given a json file that specifies build targets and rules, write a program that simulates a build engine by printing out the commands in an acceptable order based on the dependencies specified. Assume anything under src/ and include/ already exists. For any other files, there must be an entry under "targets" that produces them.

cl.exe /EHsc /Yc /Iinclude src/pch.cpp /Fp:build/pch.pch /Fo:build/
cl.exe /LD /EHsc /Yu /Iinclude /Fp:build/pch.pch src/math_utils.cpp /Fo:build/ /Fe:build/math_utils.dll
cl.exe /LD /EHsc /Yu /Iinclude /Fp:build/pch.pch src/string_helpers.cpp /Fo:build/ /Fe:build/string_helpers.dll
cl.exe /EHsc /Iinclude src/main.cpp build/math_utils.lib build/string_helpers.lib /Fo:build/ /Fe:build/program.exe
powershell -Command "Send-MailMessage -To 'user@example.com' -From 'build@example.com' -Subject 'Build Complete'"

1. Parse json into targets 
2. map  files -> producign the target -a. src/, include already existed , any other input must produce exactly one target
3. bulding the dependecy graph , graph : producer -> consumer 
4. order targets respecting dependencies, 
5. detect cycles and fail
