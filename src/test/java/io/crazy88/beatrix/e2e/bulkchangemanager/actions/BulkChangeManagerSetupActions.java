package io.crazy88.beatrix.e2e.bulkchangemanager.actions;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import com.google.common.collect.ImmutableMap;

import io.crazy88.beatrix.e2e.bulkchangemanager.csv.CsvWriter;
import io.crazy88.beatrix.e2e.bulkchangemanager.dto.BulkAccountEntry;
import io.crazy88.beatrix.e2e.bulkchangemanager.dto.JobType;

@TestComponent
public class BulkChangeManagerSetupActions {

    private static final Map<JobType, String> FILES_PER_JOB_TYPE = ImmutableMap.of(JobType.FORCE_PHONE_UPDATE, "force-phone-update.csv",
            JobType.BULK_ACCOUNT_ENTRY, "bulk-account-entry.csv");

    private static final String TARGET_CSV_FILES = "target/e2e/bulk-change/";

    private static final List<String> HEADERS_BULK_ACCOUNT_ENTRY = asList("UUID", "CategoryCode", "Reference", "Description", "Amount");

    @Autowired
    private CsvWriter csvWriter;

    public File generateBulkAccountEntryCsvFile(BulkAccountEntry bulkAccountEntry) throws IOException {
        return generateCsvFile(FILES_PER_JOB_TYPE.get(JobType.BULK_ACCOUNT_ENTRY), HEADERS_BULK_ACCOUNT_ENTRY, buildBulkAccountEntryLine(bulkAccountEntry));
    }

    public File generateForcePhoneUpdateCsvFile(String profileId) throws IOException {
        return generateCsvFile(FILES_PER_JOB_TYPE.get(JobType.FORCE_PHONE_UPDATE), singletonList("ProfileId"), singletonList(profileId));
    }

    private File generateCsvFile(String fileName, List<String> headers, List<String> fields) throws IOException {
        makeTemporalCsvFilesFolder();
        File file = new File(TARGET_CSV_FILES + fileName);
        csvWriter.writeLines(file, headers, fields);

        return file;
    }

    private List<String> buildBulkAccountEntryLine(BulkAccountEntry bulkAccountEntry) {
        return Stream.of(bulkAccountEntry.getProfileId().toString(),
                bulkAccountEntry.getCategoryCode(),
                bulkAccountEntry.getTransactionRef(),
                bulkAccountEntry.getExternalDescription(),
                bulkAccountEntry.getAmount()).collect(Collectors.toList());
    }

    private void makeTemporalCsvFilesFolder() {
        File folder = new File(TARGET_CSV_FILES);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
}
