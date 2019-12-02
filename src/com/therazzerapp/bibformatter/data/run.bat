@echo off
rem ================ Settings ================
set commands=
set save=
rem ================= Readme =================
rem Usage: -b <bibFile> [<outputPath>] [-command [[+parameter] <arguments>] ...
rem Example:
rem commands=-capitalizeValue +t article book +k title
rem ==========================================
set a="Usage: <file:bibFile> <boolean:debug> -c1 -c2 -c3 ..."
if ["%~1"]==[""] (
	echo Error: No bibliography file specified!
	echo %a%
	goto stop
)
:s2
if "%commands%"=="" (
	echo Error: No commands found!
	echo Usage: -b <bibFile> [<outputPath>] [-command [[+parameter] <arguments>] ...
	goto stop
)
echo Bibliography: %1
echo Commands: %commands%
java -jar BibFormatter.jar -b %1 %save% %commands%
goto end
:stop
pause
:end
