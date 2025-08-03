package org.artificery.dramasukiutil.data.repository

import org.artificery.dramasukiutil.data.model.DramaSukiFile
import org.jsoup.Jsoup
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

class DataSukiFolderScrapper {
    fun scrapeFilesNamesFromTable(url: String): List<DramaSukiFile> {
        val options = ChromeOptions()
        options.addArguments("--headless=new")
        val driver = ChromeDriver(options)
        driver.get(url)
        Thread.sleep(3000) // Wait for JS to load content (adjust as needed)
        val doc = Jsoup.parse(driver.pageSource ?: "")
        val folderNames = doc.select(".folder_link")
        driver.quit()
        return folderNames.mapNotNull { folderName ->
            val dramaSukiFile = getDetailsFromDataSukiFolderName(folderName.text())
            dramaSukiFile
        }
    }

    private fun getDetailsFromDataSukiFolderName(folderName: String): DramaSukiFile? {
        val regex = Regex("""^(.*?)\s*(?:\(([^()]+)\))?\s*\((\d{4})\).*$""")
        val match = regex.find(folderName.trim())
        if (match != null) {
            val (title, altOrYear, yearStr) = match.destructured
            val year = yearStr.toLong()
            val alternativeTitle = if (altOrYear.matches(Regex("""\d{4}"""))) null else altOrYear.takeIf { it.isNotBlank() }
            return DramaSukiFile(
                folderName = folderName.trim(),
                title = title.trim(),
                year = year,
                alternativeTitle = alternativeTitle?.trim()
            )
        } else {
            println("Could not parse folder name: $folderName")
            return null
        }
    }
}