<?php

namespace App\Entity;

use App\Repository\SeanceRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: SeanceRepository::class)]
class Seance
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    private ?string $titreSeance = null;

    #[ORM\Column(length: 255)]
    private ?string $descriptionSeance = null;

    #[ORM\Column]
    private ?int $nbParticipantSeance = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $dateDebutSeance = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $dateFinSeance = null;

    #[ORM\OneToMany(mappedBy: 'seance', targetEntity: ParticipationSeance::class)]
    private Collection $participation;

    #[ORM\OneToMany(mappedBy: 'seance', targetEntity: Video::class)]
    private Collection $video;

    public function __construct()
    {
        $this->participation = new ArrayCollection();
        $this->video = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getTitreSeance(): ?string
    {
        return $this->titreSeance;
    }

    public function setTitreSeance(string $titreSeance): self
    {
        $this->titreSeance = $titreSeance;

        return $this;
    }

    public function getDescriptionSeance(): ?string
    {
        return $this->descriptionSeance;
    }

    public function setDescriptionSeance(string $descriptionSeance): self
    {
        $this->descriptionSeance = $descriptionSeance;

        return $this;
    }

    public function getNbParticipantSeance(): ?int
    {
        return $this->nbParticipantSeance;
    }

    public function setNbParticipantSeance(int $nbParticipantSeance): self
    {
        $this->nbParticipantSeance = $nbParticipantSeance;

        return $this;
    }

    public function getDateDebutSeance(): ?\DateTimeInterface
    {
        return $this->dateDebutSeance;
    }

    public function setDateDebutSeance(\DateTimeInterface $dateDebutSeance): self
    {
        $this->dateDebutSeance = $dateDebutSeance;

        return $this;
    }

    public function getDateFinSeance(): ?\DateTimeInterface
    {
        return $this->dateFinSeance;
    }

    public function setDateFinSeance(\DateTimeInterface $dateFinSeance): self
    {
        $this->dateFinSeance = $dateFinSeance;

        return $this;
    }

    /**
     * @return Collection<int, ParticipationSeance>
     */
    public function getParticipation(): Collection
    {
        return $this->participation;
    }

    public function addParticipation(ParticipationSeance $participation): self
    {
        if (!$this->participation->contains($participation)) {
            $this->participation->add($participation);
            $participation->setSeance($this);
        }

        return $this;
    }

    public function removeParticipation(ParticipationSeance $participation): self
    {
        if ($this->participation->removeElement($participation)) {
            // set the owning side to null (unless already changed)
            if ($participation->getSeance() === $this) {
                $participation->setSeance(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection<int, Video>
     */
    public function getVideo(): Collection
    {
        return $this->video;
    }

    public function addVideo(Video $video): self
    {
        if (!$this->video->contains($video)) {
            $this->video->add($video);
            $video->setSeance($this);
        }

        return $this;
    }

    public function removeVideo(Video $video): self
    {
        if ($this->video->removeElement($video)) {
            // set the owning side to null (unless already changed)
            if ($video->getSeance() === $this) {
                $video->setSeance(null);
            }
        }

        return $this;
    }
}
