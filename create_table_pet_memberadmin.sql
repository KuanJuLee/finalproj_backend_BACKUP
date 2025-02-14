-- �Ыظ�Ʈw
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'meowdb')
BEGIN
    CREATE DATABASE meowdb;
END
GO

USE meowdb;
GO


-- �Ыذ�¦���
-- �Ы� Admin ��
CREATE TABLE Admin (
    adminId INT IDENTITY(1,1) PRIMARY KEY,
    adminName NVARCHAR(20) NOT NULL UNIQUE,
    password NVARCHAR(20) NOT NULL,
    createDate DATETIME NOT NULL DEFAULT GETDATE(),
    updateDate DATETIME NOT NULL DEFAULT GETDATE()
);
GO

-- �Ы� Member ��
CREATE TABLE Member (
    memberId INT IDENTITY(1,1) PRIMARY KEY,
    nickName NVARCHAR(20) NOT NULL UNIQUE,
    password NVARCHAR(20) NOT NULL,
    name NVARCHAR(70) NOT NULL,
    email NVARCHAR(100) NOT NULL UNIQUE,
    phone NVARCHAR(10) NOT NULL,
    address NVARCHAR(100) NOT NULL,
    birthday DATE NOT NULL,
    createDate DATETIME NOT NULL DEFAULT GETDATE(),
    updateDate DATETIME NOT NULL DEFAULT GETDATE(),
    lineId NVARCHAR(50) NULL,
    lineName NVARCHAR(50) NULL,
    linePicture NVARCHAR(255) NULL,
    followed BIT NOT NULL DEFAULT 0,
    userType BIT NOT NULL
);
GO

-- �Ы� Species ��
CREATE TABLE Species (
    speciesId INT IDENTITY(1,1) PRIMARY KEY,
    species NVARCHAR(10) NOT NULL
);
GO

-- �Ы� Breed ��
CREATE TABLE Breed (
    breedId INT IDENTITY(1,1) PRIMARY KEY,
    breed NVARCHAR(50) NOT NULL
);
GO

-- �Ы� FurColor ��
CREATE TABLE FurColor (
    furColorId INT IDENTITY(1,1) PRIMARY KEY,
    furColor NVARCHAR(20) NOT NULL
);
GO

-- �Ы� CaseState ��
CREATE TABLE CaseState (
    caseStateId INT IDENTITY(1,1) PRIMARY KEY,
    caseStatement NVARCHAR(5) NOT NULL
);
GO

-- �إ� City ��
CREATE TABLE City (
    cityId INT IDENTITY(1,1) PRIMARY KEY,
    city NVARCHAR(5) NOT NULL
);
GO

-- �إ� DistrictArea ��
CREATE TABLE DistrictArea (
    districtAreaId INT IDENTITY(1,1) PRIMARY KEY,
    districtAreaName NVARCHAR(5) NOT NULL,
    cityId INT NOT NULL,
    FOREIGN KEY (cityId) REFERENCES City(cityId) ON DELETE CASCADE
);
GO

-- �إ� CanAfford ��
CREATE TABLE CanAfford (
    canAffordId INT IDENTITY(1,1) PRIMARY KEY,
    canAfford NVARCHAR(10) NOT NULL
);
GO

-- �إ� RescueDemand ��
CREATE TABLE RescueDemand (
    rescueDemandId INT IDENTITY(1,1) PRIMARY KEY,
    rescueDemand NVARCHAR(10) NOT NULL
);
GO

-- �إ� RescueProgress ��
CREATE TABLE RescueProgress (
    rescueProgressId INT IDENTITY(1,1) PRIMARY KEY,
    progressDetail NVARCHAR(MAX) NOT NULL,
    createTime DATETIME NOT NULL DEFAULT GETDATE(),
    imageUrl NVARCHAR(255) NULL
);
GO



-- 2. �Ыخץ������



-- �Ы� AdoptionCase ��
CREATE TABLE AdoptionCase (
    adoptionCaseId INT IDENTITY(1,1) PRIMARY KEY,
    memberId INT NOT NULL,
    speciesId INT NOT NULL,
    breedId INT NULL,
    furColorId INT NULL,
    cityId INT NOT NULL,
    districtAreaId INT NOT NULL,
    caseTitle NVARCHAR(30) NOT NULL,
    gender NVARCHAR(10) NULL,
    sterilization NVARCHAR(5) NULL,
    age INT NULL,
    microChipNumber INT NULL,
    viewCount INT NULL,
    follow INT NULL,
    publicationTime DATETIME NOT NULL DEFAULT GETDATE(),
    lastUpdateTime DATETIME NOT NULL DEFAULT GETDATE(),
    story NVARCHAR(MAX) NOT NULL,
    healthCondition NVARCHAR(MAX) NOT NULL,
    adoptedCondition NVARCHAR(MAX) NOT NULL,
    note NVARCHAR(MAX) NULL,
    caseStateId INT NOT NULL,
    FOREIGN KEY (memberId) REFERENCES Member(memberId) ON DELETE CASCADE,
    FOREIGN KEY (speciesId) REFERENCES Species(speciesId),
    FOREIGN KEY (breedId) REFERENCES Breed(breedId),
    FOREIGN KEY (furColorId) REFERENCES FurColor(furColorId),
    FOREIGN KEY (cityId) REFERENCES City(cityId),
    FOREIGN KEY (districtAreaId) REFERENCES DistrictArea(districtAreaId),
    FOREIGN KEY (caseStateId) REFERENCES CaseState(caseStateId)
);
GO

-- �Ы� LostCase ��
CREATE TABLE LostCase (
    lostCaseId INT IDENTITY(1,1) PRIMARY KEY,
    memberId INT NOT NULL,
    speciesId INT NOT NULL,
    breedId INT NULL,
    furColorId INT NULL,
    cityId INT NOT NULL,
    districtAreaId INT NOT NULL,
    caseTitle NVARCHAR(30) NOT NULL,
    gender NVARCHAR(10) NULL,
    sterilization NVARCHAR(5) NULL,
    age INT NULL,
    microChipNumber INT NULL,
    publicationTime DATETIME NOT NULL DEFAULT GETDATE(),
    lastUpdateTime DATETIME NOT NULL DEFAULT GETDATE(),
    lostExperience NVARCHAR(MAX) NOT NULL,
    contactInformation NVARCHAR(MAX) NULL,
    featureDescription NVARCHAR(MAX) NOT NULL,
    caseStateId INT NOT NULL,
    FOREIGN KEY (memberId) REFERENCES Member(memberId) ON DELETE CASCADE,
    FOREIGN KEY (speciesId) REFERENCES Species(speciesId),
    FOREIGN KEY (breedId) REFERENCES Breed(breedId),
    FOREIGN KEY (furColorId) REFERENCES FurColor(furColorId),
    FOREIGN KEY (cityId) REFERENCES City(cityId),
    FOREIGN KEY (districtAreaId) REFERENCES DistrictArea(districtAreaId),
    FOREIGN KEY (caseStateId) REFERENCES CaseState(caseStateId)
);
GO

-- �Ы� RescueCase ��
CREATE TABLE RescueCase (
    rescueCaseId INT IDENTITY(1,1) PRIMARY KEY,
    memberId INT NOT NULL,
    speciesId INT NOT NULL,
    breedId INT NULL,
    furColorId INT NULL,
    cityId INT NOT NULL,
    districtAreaId INT NOT NULL,
    caseTitle NVARCHAR(30) NOT NULL,
    gender NVARCHAR(10) NULL,
    sterilization NVARCHAR(5) NULL,
    age INT NULL,
    microChipNumber INT NULL,
    viewCount INT NULL,
    follow INT NULL,
    publicationTime DATETIME NOT NULL DEFAULT GETDATE(),
    lastUpdateTime DATETIME NOT NULL DEFAULT GETDATE(),
    rescueReason NVARCHAR(MAX) NOT NULL,
    caseStateId INT NOT NULL,
    FOREIGN KEY (memberId) REFERENCES Member(memberId) ON DELETE CASCADE,
    FOREIGN KEY (speciesId) REFERENCES Species(speciesId),
    FOREIGN KEY (breedId) REFERENCES Breed(breedId),
    FOREIGN KEY (furColorId) REFERENCES FurColor(furColorId),
    FOREIGN KEY (cityId) REFERENCES City(cityId),
    FOREIGN KEY (districtAreaId) REFERENCES DistrictArea(districtAreaId),
    FOREIGN KEY (caseStateId) REFERENCES CaseState(caseStateId)
);
GO


-- 3. �ЫػP�ץ��������]�~��̿�ץ��^



-- �إ� AdoptionCaseApply ��
CREATE TABLE AdoptionCaseApply (
    adoptionCaseApplyId INT IDENTITY(1,1) PRIMARY KEY,
    memberId INT NOT NULL,
    introduction NVARCHAR(MAX) NOT NULL,
    applicationStatus BIT NOT NULL,
    FOREIGN KEY (memberId) REFERENCES Member(memberId) ON DELETE CASCADE
);
GO

-- �إ� Follow ��
CREATE TABLE Follow (
    followId INT IDENTITY(1,1) PRIMARY KEY,
    memberId INT NOT NULL,
    adoptionCaseId INT NULL,
    followDate DATETIME NOT NULL DEFAULT GETDATE(),
    notificationsEnabled BIT NOT NULL DEFAULT 1,
    FOREIGN KEY (memberId) REFERENCES Member(memberId) ON DELETE CASCADE,
    FOREIGN KEY (adoptionCaseId) REFERENCES AdoptionCase(adoptionCaseId) ON DELETE CASCADE
);
GO



-- �Ы� CasePicture ��]�ץ�Ϥ��^
CREATE TABLE CasePicture (
    casePictureId INT IDENTITY(1,1) PRIMARY KEY,
    rescueCaseId INT NULL,
    lostCaseId INT NULL,
    adoptionCaseId INT NULL,
    pictureUrl NVARCHAR(255) NOT NULL,
    uploadedAt DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (rescueCaseId) REFERENCES RescueCase(rescueCaseId) ON DELETE SET NULL,
    FOREIGN KEY (lostCaseId) REFERENCES LostCase(lostCaseId) ON DELETE SET NULL,
    FOREIGN KEY (adoptionCaseId) REFERENCES AdoptionCase(adoptionCaseId) ON DELETE SET NULL
);
GO

-- �إ� CanAfford_RescueCase ��
CREATE TABLE [dbo].[CanAfford_RescueCase] (
    [canAffordId] INT NOT NULL PRIMARY KEY,
    [rescueCaseId] INT NOT NULL,
    CONSTRAINT [FK_CanAfford_RescueCase_CanAfford] FOREIGN KEY ([canAffordId]) REFERENCES [dbo].[CanAfford] ([canAffordId]) ON DELETE CASCADE,
    CONSTRAINT [FK_CanAfford_RescueCase_RescueCase] FOREIGN KEY ([rescueCaseId]) REFERENCES [dbo].[RescueCase] ([rescueCaseId]) ON DELETE CASCADE
);
GO

-- �إ� Case_CaseApply ��
CREATE TABLE [dbo].[Case_CaseApply] (
    [adoptionCaseApplyId] INT NOT NULL PRIMARY KEY,
    [adoptionCaseId] INT NOT NULL,
    CONSTRAINT [FK_Case_CaseApply_AdoptionCase] FOREIGN KEY ([adoptionCaseId]) REFERENCES [dbo].[AdoptionCase] ([adoptionCaseId]) ON DELETE CASCADE,
    CONSTRAINT [FK_Case_CaseApply_AdoptionCaseApply] FOREIGN KEY ([adoptionCaseApplyId]) REFERENCES [dbo].[AdoptionCaseApply] ([adoptionCaseApplyId]) ON DELETE CASCADE
);
GO

-- �إ� RescueCase_RescueDemand ���p��
CREATE TABLE [dbo].[RescueCase_RescueDemand] (
    [rescueCaseId] INT NOT NULL,
    [rescueDemandId] INT NOT NULL PRIMARY KEY, -- �H rescueDemandId �@���D��
    CONSTRAINT [FK_RescueCase_RescueDemand_RescueCase] FOREIGN KEY ([rescueCaseId]) REFERENCES [dbo].[RescueCase] ([rescueCaseId]) ON DELETE CASCADE,
    CONSTRAINT [FK_RescueCase_RescueDemand_RescueDemand] FOREIGN KEY ([rescueDemandId]) REFERENCES [dbo].[RescueDemand] ([rescueDemandId]) ON DELETE CASCADE
);
GO


-- �Ы� ReportCase ��]�|���ץ�^
CREATE TABLE ReportCase (
    reportId INT IDENTITY(1,1) PRIMARY KEY,
    rescueCaseId INT NULL,
    lostCaseId INT NULL,
    adoptionCaseId INT NULL,
    memberId INT NOT NULL,
    adminId INT NULL,
    reportDate DATETIME NOT NULL DEFAULT GETDATE(),
    updateDate DATETIME NULL,
    reportTitle NVARCHAR(30) NOT NULL,
    reportNotes NVARCHAR(MAX) NULL,
    reportState BIT NOT NULL DEFAULT 0,
    FOREIGN KEY (rescueCaseId) REFERENCES RescueCase(rescueCaseId) ON DELETE SET NULL,
    FOREIGN KEY (lostCaseId) REFERENCES LostCase(lostCaseId) ON DELETE SET NULL,
    FOREIGN KEY (adoptionCaseId) REFERENCES AdoptionCase(adoptionCaseId) ON DELETE SET NULL,
    FOREIGN KEY (memberId) REFERENCES Member(memberId) ON DELETE CASCADE,
    FOREIGN KEY (adminId) REFERENCES Admin(adminId) ON DELETE SET NULL
);
GO


-- 4. �Ыب�L�\���

-- �إ� Activity ��
CREATE TABLE Activity (
    activityId INT IDENTITY(1,1) PRIMARY KEY,
    memberId INT NOT NULL,
    title NVARCHAR(30) NOT NULL,
    purpose NVARCHAR(30) NULL,
    description NVARCHAR(MAX) NULL,
    location NVARCHAR(MAX) NULL,
    startTime DATETIME NULL,
    endTime DATETIME NULL,
    maxParticipants INT NULL,
    createdAt DATETIME NOT NULL DEFAULT GETDATE(),
    updatedAt DATETIME NOT NULL DEFAULT GETDATE(),
    activityStatus VARCHAR(5) NULL,
    adminPermission BIT NULL,
    reward INT NULL,
    FOREIGN KEY (memberId) REFERENCES Member(memberId) ON DELETE CASCADE
);
GO

-- �Ы� ActivityParticipantList ��
CREATE TABLE ActivityParticipantList (
    participantId INT IDENTITY(1,1) PRIMARY KEY,
    activityId INT NOT NULL,
    memberId INT NOT NULL,
    registrationTime DATETIME NOT NULL DEFAULT GETDATE(),
    status NVARCHAR(5) NULL,
    FOREIGN KEY (activityId) REFERENCES Activity(activityId) ON DELETE CASCADE,
    FOREIGN KEY (memberId) REFERENCES Member(memberId) ON DELETE CASCADE
);
GO

-- �إ� Banner ��
CREATE TABLE Banner (
    bannerId INT IDENTITY(1,1) PRIMARY KEY,
    onlineDate DATETIME NOT NULL,
    dueDate DATETIME NOT NULL,
    bannerType NVARCHAR(20) NOT NULL,
    isHidden BIT NOT NULL DEFAULT 0
);
GO

-- ���J BannerType ENUM ������
INSERT INTO Banner (bannerType) VALUES 
('LOST'), ('ADOPTION'), ('RESCUE');
GO

-- �Ы� LineTemporaryBinding ��]LINE �j�w�Ȧs�^
CREATE TABLE LineTemporaryBinding (
    id INT IDENTITY(1,1) PRIMARY KEY,
    bindingToken NVARCHAR(255) NOT NULL,
    lineId NVARCHAR(255) NOT NULL,
    expiryTime DATETIME NOT NULL
);
GO