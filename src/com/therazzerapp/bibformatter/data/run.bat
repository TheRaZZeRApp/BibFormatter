@echo off
rem ================ Settings ================
set debug=false
set commands=
rem ================= Readme =================
rem Usage: <file:bibFile> <boolean:debug> -c1 pn -c2 pn -cn pn...
rem Commands:
rem -capitalizeValue (key1) [keyn]
rem -orderEntries [keyn | file]
rem -formatMonth (number | name)
rem -formatPages (single | double)
rem -removeEntry (key1) [keyn]
rem Example:
rem commands=-capitalizeValue title author -orderEntries issn number url author title -formatMonth number -formatPages double -removeEntry url issn doi pages
rem ==========================================
set a="Usage: <file:bibFile> <boolean:debug> -c1 -c2 -c3 ..."
if ["%~1"]==[""] (
	echo Error: No bib file specified!
	echo %a%
	goto stop
)
if "%debug%" NEQ "true" (
	if "%debug%" NEQ "false" (
		echo Error: Set debug to true/false!
		echo %a%
		goto stop
	) else (
		goto s2
	)
	echo Error: Set debug to true/false!
	echo %a%
	goto stop
)
:s2
if "%commands%"=="" (
	echo Error: No commands found
	echo %a%
	goto stop
)
echo Commands: %commands%
java -jar BibFormatter.jar %1 %debug% %commands%
goto ende
:stop
pause
:ende