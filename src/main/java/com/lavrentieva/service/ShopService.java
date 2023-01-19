package com.lavrentieva.service;


import com.lavrentieva.InvalidLineException;
import com.lavrentieva.model.HeaderPriceList;
import com.lavrentieva.model.Invoice;
import com.lavrentieva.model.goods.Electronics;
import com.lavrentieva.model.goods.ElectronicsType;
import com.lavrentieva.model.goods.Telephone;
import com.lavrentieva.model.goods.Television;
import com.lavrentieva.repository.PriceListRepository;
import com.lavrentieva.repository.ShopRepository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class ShopService {
    private static ShopService instance;
    private final PriceListRepository priceRepository = new PriceListRepository();
    private final ShopRepository shopRepository;
    private final PersonService personService = new PersonService();
    private List<String[]> dataForCsvFile = new ArrayList<>();
    private final List<Map<String, String>> priceList = priceRepository.getPriceList();
    private BufferedReader reader;
    private final Random random = new Random();

    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public static ShopService getInstance() {
        if (instance == null) {
            instance = new ShopService(ShopRepository.getInstance());
        }
        return instance;
    }

    public Invoice createInvoice(int limit) {
        Invoice invoice = new Invoice(createListOfGoods(), limit);
        shopRepository.save(invoice);
        return invoice;
    }

    public void createInvoiceFromAmount(int amount, int limit) {
        try {
            for (int i = 0; i < amount; i++) {
                createInvoice(limit);
                //додала паузу щоб був різний час для інвойсів (для одного з аналітичних методів)
                Thread.sleep(1000);
            }
        } catch (InterruptedException i) {
            i.printStackTrace();
        }
    }

    public void printAllInvoices() {
        List<Invoice> allInvoices = shopRepository.getInvoices();
        System.out.println(allInvoices);
    }

    private List<Electronics> createListOfGoods() {
        Objects.requireNonNull(priceList);
        final List<Electronics> listOfGoods = new ArrayList<>();
        final Integer[] indexes = generateIndexes();
        for (int i = 0; i < indexes.length; i++) {
            Map<String, String> map = priceList.get(indexes[i]);
            Objects.requireNonNullElse(map, priceList.get(indexes[0]));
            ElectronicsType typeFromMap = ElectronicsType.valueOf(map.get("TYPE"));
            if (typeFromMap.equals(ElectronicsType.TELEPHONE)) {
                Telephone telephone = createTelephoneFromMap(map);
                listOfGoods.add(telephone);
            } else {
                Television television = createTelevisionFromMap(map);
                listOfGoods.add(television);
            }
        }
        return listOfGoods;
    }

    private Integer[] generateIndexes() {
        int randomAmountOfGoods = random.nextInt(1, 5);
        Integer[] indexes = new Integer[randomAmountOfGoods];
        for (int i = 0; i < randomAmountOfGoods; i++) {
            indexes[i] = generateRandomIndex();
        }
        return indexes;
    }

    private int generateRandomIndex() {
        Objects.requireNonNull(priceList);
        return random.nextInt(0, priceList.size() - 1);
    }

    private static Telephone createTelephoneFromMap(final Map<String, String> map) {
        Objects.requireNonNull(map);
        Telephone telephone = new Telephone(ElectronicsType.TELEPHONE);
        createGeneralElectronicsFieldsFromMap(map, telephone);
        telephone.setModel(map.get("MODEL"));
        return telephone;
    }

    private static Television createTelevisionFromMap(final Map<String, String> map) {
        Objects.requireNonNull(map);
        Television television = new Television(ElectronicsType.TELEVISION);
        createGeneralElectronicsFieldsFromMap(map, television);
        int diagonal = Integer.parseInt(map.get("DIAGONAL"));
        television.setDiagonal(diagonal);
        television.setCountry(map.get("COUNTRY"));
        return television;
    }

    private static void createGeneralElectronicsFieldsFromMap(
            final Map<String, String> map, final Electronics electronics) {
        Objects.requireNonNull(map);
        ElectronicsType electronicsType = ElectronicsType.valueOf(map.get("TYPE"));
        electronics.setType(electronicsType);
        electronics.setSeries(map.get("SERIES"));
        electronics.setScreenType(map.get("SCREEN_TYPE"));
        int price = Integer.parseInt(map.get("PRICE"));
        electronics.setPrice(price);
    }

    public void readFromCsvFileData() throws IOException {
        try {
            readFromResources();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String[] header = null;
        String line = "";
        boolean checkFirstLine = true;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            if (checkLineIsEmpty(values)) {
                return;
            }
            if (checkFirstLine) {
                header = values;
                checkFirstLine = false;
            } else {
                Map<String, String> recordPriceList = new HashMap<>();
                for (int i = 0; i < header.length; i++) {
                    recordPriceList.put(header[i], values[i]);
                }
                priceRepository.save(recordPriceList);
            }
        }
    }

    private void readFromResources() throws FileNotFoundException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = loader.getResourceAsStream("priceList.csv");
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        reader = new BufferedReader(streamReader);
    }

    private boolean checkLineIsEmpty(String[] values) {
        for (int i = 0; i < values.length; i++) {
            if (values[i].trim().isEmpty()) {
                try {
                    throw new InvalidLineException(
                            "There is an invalid line, it will not be read");
                } catch (InvalidLineException invalidLineException) {
                    invalidLineException.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }

    public void writeToCsvFileData() {
        final File file = new File("src/main/resources", "priceList.csv");
        try (FileWriter priceListWriter = new FileWriter(file)) {
            priceListWriter.write(convertHeadToCsv());
            Iterator<String> i = createDataForCsvFile().iterator();
            while (i.hasNext()) {
                priceListWriter.write(i.next());
            }
            priceListWriter.write(createInvalidData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createInvalidData() {
        return "TELEVISION,,none,60,LED,Chine,625\n";
    }

    private List<String> createDataForCsvFile() {
        final List<String[]> dataForCsvFile = fillListWithData();
        final List<String> dataForCsvFileAfterPrepare = dataForCsvFile
                .stream()
                .map(this::convertToCsv)
                .collect(Collectors.toList());
        return dataForCsvFileAfterPrepare;
    }

    private List<String[]> fillListWithData() {
        dataForCsvFile.add(new String[]
                {"TELEPHONE", "X 12", "Xiaomi", "none", "AMOLED", "none", "500"});
        dataForCsvFile.add(new String[]
                {"TELEPHONE", "S 15", "Samsung", "none", "AMOLED", "none", "375"});
        dataForCsvFile.add(new String[]
                {"TELEPHONE", "iPhone 13", "Apple", "none", "XDR OLED", "none", "900"});
        dataForCsvFile.add(new String[]
                {"TELEPHONE", "Xperia Pro-I", "Sony", "none", "OLED", "none", "1175"});
        dataForCsvFile.add(new String[]
                {"TELEPHONE", "Pixel 7", "Google", "none", "AMOLED", "none", "1375"});

        dataForCsvFile.add(new String[]
                {"TELEVISION", "Mi TV Q1E", "none", "65", "QLED", "China", "700"});
        dataForCsvFile.add(new String[]
                {"TELEVISION", "55PUS", "none", "55", "LED", "Netherlands", "550"});
        dataForCsvFile.add(new String[]
                {"TELEVISION", "TV40-FS2G", "none", "40", "LED", "United Kingdom", "250"});
        dataForCsvFile.add(new String[]
                {"TELEVISION", "UE43AU7100UX", "none", "43", "LED", "South Korea", "425"});
        dataForCsvFile.add(new String[]
                {"TELEVISION", "43UQ75006LF", "none", "43", "LED", "South Korea", "375"});
        return dataForCsvFile;
    }

    private String convertToCsv(String[] values) {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            line.append(values[i]);
            if (i != values.length - 1) {
                line.append(",");
            }
        }
        line.append("\n");
        return line.toString();
    }

    private String convertHeadToCsv() {
        final HeaderPriceList[] values = HeaderPriceList.values();
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            line.append(values[i]);
            if (i != values.length - 1) {
                line.append(",");
            }
        }
        line.append("\n");
        return line.toString();
    }
}
