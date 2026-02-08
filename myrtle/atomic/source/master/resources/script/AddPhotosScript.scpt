on run argv
    set imagePath to item 1 of argv
    set albumName to item 2 of argv
    
    tell application "Photos"
        activate
        set imageFile to POSIX file imagePath as alias
        set importedMedia to import {imageFile} skip check duplicates yes
        
        if not (exists container named albumName) then
            make new album named albumName
        end if
        
        add importedMedia to album albumName
    end tell
end run